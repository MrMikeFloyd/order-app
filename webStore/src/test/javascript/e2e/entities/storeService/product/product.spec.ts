import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ProductComponentsPage, ProductDeleteDialog, ProductUpdatePage } from './product.page-object';

const expect = chai.expect;

describe('Product e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productComponentsPage: ProductComponentsPage;
  let productUpdatePage: ProductUpdatePage;
  let productDeleteDialog: ProductDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Products', async () => {
    await navBarPage.goToEntity('product');
    productComponentsPage = new ProductComponentsPage();
    await browser.wait(ec.visibilityOf(productComponentsPage.title), 5000);
    expect(await productComponentsPage.getTitle()).to.eq('webStoreApp.storeServiceProduct.home.title');
    await browser.wait(ec.or(ec.visibilityOf(productComponentsPage.entities), ec.visibilityOf(productComponentsPage.noResult)), 1000);
  });

  it('should load create Product page', async () => {
    await productComponentsPage.clickOnCreateButton();
    productUpdatePage = new ProductUpdatePage();
    expect(await productUpdatePage.getPageTitle()).to.eq('webStoreApp.storeServiceProduct.home.createOrEditLabel');
    await productUpdatePage.cancel();
  });

  it('should create and save Products', async () => {
    const nbButtonsBeforeCreate = await productComponentsPage.countDeleteButtons();

    await productComponentsPage.clickOnCreateButton();

    await promise.all([
      productUpdatePage.setNameInput('name'),
      productUpdatePage.setSkuInput('sku'),
      productUpdatePage.setDescriptionInput('description'),
      productUpdatePage.setSrpInput('5'),
      productUpdatePage.salesUnitSelectLastOption(),
      productUpdatePage.setSalesQuantityInput('5'),
      productUpdatePage.statusSelectLastOption(),
      productUpdatePage.setGrosWeightInput('5'),
      productUpdatePage.setNetWeightInput('5'),
      productUpdatePage.setLengthInput('5'),
      productUpdatePage.setWidthInput('5'),
      productUpdatePage.setHeightInput('5'),
    ]);

    expect(await productUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productUpdatePage.getSkuInput()).to.eq('sku', 'Expected Sku value to be equals to sku');
    expect(await productUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await productUpdatePage.getSrpInput()).to.eq('5', 'Expected srp value to be equals to 5');
    const selectedTaxable = productUpdatePage.getTaxableInput();
    if (await selectedTaxable.isSelected()) {
      await productUpdatePage.getTaxableInput().click();
      expect(await productUpdatePage.getTaxableInput().isSelected(), 'Expected taxable not to be selected').to.be.false;
    } else {
      await productUpdatePage.getTaxableInput().click();
      expect(await productUpdatePage.getTaxableInput().isSelected(), 'Expected taxable to be selected').to.be.true;
    }
    expect(await productUpdatePage.getSalesQuantityInput()).to.eq('5', 'Expected salesQuantity value to be equals to 5');
    expect(await productUpdatePage.getGrosWeightInput()).to.eq('5', 'Expected grosWeight value to be equals to 5');
    expect(await productUpdatePage.getNetWeightInput()).to.eq('5', 'Expected netWeight value to be equals to 5');
    expect(await productUpdatePage.getLengthInput()).to.eq('5', 'Expected length value to be equals to 5');
    expect(await productUpdatePage.getWidthInput()).to.eq('5', 'Expected width value to be equals to 5');
    expect(await productUpdatePage.getHeightInput()).to.eq('5', 'Expected height value to be equals to 5');

    await productUpdatePage.save();
    expect(await productUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Product', async () => {
    const nbButtonsBeforeDelete = await productComponentsPage.countDeleteButtons();
    await productComponentsPage.clickOnLastDeleteButton();

    productDeleteDialog = new ProductDeleteDialog();
    expect(await productDeleteDialog.getDialogTitle()).to.eq('webStoreApp.storeServiceProduct.delete.question');
    await productDeleteDialog.clickOnConfirmButton();

    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
