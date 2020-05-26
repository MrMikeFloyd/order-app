import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { SalesOrderItemComponentsPage, SalesOrderItemDeleteDialog, SalesOrderItemUpdatePage } from './sales-order-item.page-object';

const expect = chai.expect;

describe('SalesOrderItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let salesOrderItemComponentsPage: SalesOrderItemComponentsPage;
  let salesOrderItemUpdatePage: SalesOrderItemUpdatePage;
  let salesOrderItemDeleteDialog: SalesOrderItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SalesOrderItems', async () => {
    await navBarPage.goToEntity('sales-order-item');
    salesOrderItemComponentsPage = new SalesOrderItemComponentsPage();
    await browser.wait(ec.visibilityOf(salesOrderItemComponentsPage.title), 5000);
    expect(await salesOrderItemComponentsPage.getTitle()).to.eq('webStoreApp.orderServiceSalesOrderItem.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(salesOrderItemComponentsPage.entities), ec.visibilityOf(salesOrderItemComponentsPage.noResult)),
      1000
    );
  });

  it('should load create SalesOrderItem page', async () => {
    await salesOrderItemComponentsPage.clickOnCreateButton();
    salesOrderItemUpdatePage = new SalesOrderItemUpdatePage();
    expect(await salesOrderItemUpdatePage.getPageTitle()).to.eq('webStoreApp.orderServiceSalesOrderItem.home.createOrEditLabel');
    await salesOrderItemUpdatePage.cancel();
  });

  it('should create and save SalesOrderItems', async () => {
    const nbButtonsBeforeCreate = await salesOrderItemComponentsPage.countDeleteButtons();

    await salesOrderItemComponentsPage.clickOnCreateButton();

    await promise.all([
      salesOrderItemUpdatePage.setNameInput('name'),
      salesOrderItemUpdatePage.setSkuInput('sku'),
      salesOrderItemUpdatePage.setGrosWeightInput('5'),
      salesOrderItemUpdatePage.setShippedInput('2000-12-31'),
      salesOrderItemUpdatePage.setDeliveredInput('2000-12-31'),
      salesOrderItemUpdatePage.statusSelectLastOption(),
      salesOrderItemUpdatePage.setQuantityInput('5'),
      salesOrderItemUpdatePage.setUnitPriceInput('5'),
      salesOrderItemUpdatePage.setAmountInput('5'),
      salesOrderItemUpdatePage.salesOrderSelectLastOption(),
    ]);

    expect(await salesOrderItemUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await salesOrderItemUpdatePage.getSkuInput()).to.eq('sku', 'Expected Sku value to be equals to sku');
    const selectedTaxable = salesOrderItemUpdatePage.getTaxableInput();
    if (await selectedTaxable.isSelected()) {
      await salesOrderItemUpdatePage.getTaxableInput().click();
      expect(await salesOrderItemUpdatePage.getTaxableInput().isSelected(), 'Expected taxable not to be selected').to.be.false;
    } else {
      await salesOrderItemUpdatePage.getTaxableInput().click();
      expect(await salesOrderItemUpdatePage.getTaxableInput().isSelected(), 'Expected taxable to be selected').to.be.true;
    }
    expect(await salesOrderItemUpdatePage.getGrosWeightInput()).to.eq('5', 'Expected grosWeight value to be equals to 5');
    expect(await salesOrderItemUpdatePage.getShippedInput()).to.eq('2000-12-31', 'Expected shipped value to be equals to 2000-12-31');
    expect(await salesOrderItemUpdatePage.getDeliveredInput()).to.eq('2000-12-31', 'Expected delivered value to be equals to 2000-12-31');
    expect(await salesOrderItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await salesOrderItemUpdatePage.getUnitPriceInput()).to.eq('5', 'Expected unitPrice value to be equals to 5');
    expect(await salesOrderItemUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');

    await salesOrderItemUpdatePage.save();
    expect(await salesOrderItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await salesOrderItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SalesOrderItem', async () => {
    const nbButtonsBeforeDelete = await salesOrderItemComponentsPage.countDeleteButtons();
    await salesOrderItemComponentsPage.clickOnLastDeleteButton();

    salesOrderItemDeleteDialog = new SalesOrderItemDeleteDialog();
    expect(await salesOrderItemDeleteDialog.getDialogTitle()).to.eq('webStoreApp.orderServiceSalesOrderItem.delete.question');
    await salesOrderItemDeleteDialog.clickOnConfirmButton();

    expect(await salesOrderItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
