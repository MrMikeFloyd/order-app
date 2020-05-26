import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomerComponentsPage, CustomerDeleteDialog, CustomerUpdatePage } from './customer.page-object';

const expect = chai.expect;

describe('Customer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customerComponentsPage: CustomerComponentsPage;
  let customerUpdatePage: CustomerUpdatePage;
  let customerDeleteDialog: CustomerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Customers', async () => {
    await navBarPage.goToEntity('customer');
    customerComponentsPage = new CustomerComponentsPage();
    await browser.wait(ec.visibilityOf(customerComponentsPage.title), 5000);
    expect(await customerComponentsPage.getTitle()).to.eq('webStoreApp.customer.home.title');
    await browser.wait(ec.or(ec.visibilityOf(customerComponentsPage.entities), ec.visibilityOf(customerComponentsPage.noResult)), 1000);
  });

  it('should load create Customer page', async () => {
    await customerComponentsPage.clickOnCreateButton();
    customerUpdatePage = new CustomerUpdatePage();
    expect(await customerUpdatePage.getPageTitle()).to.eq('webStoreApp.customer.home.createOrEditLabel');
    await customerUpdatePage.cancel();
  });

  it('should create and save Customers', async () => {
    const nbButtonsBeforeCreate = await customerComponentsPage.countDeleteButtons();

    await customerComponentsPage.clickOnCreateButton();

    await promise.all([
      customerUpdatePage.setNameInput('name'),
      customerUpdatePage.setPhoneNumberInput('phoneNumber'),
      customerUpdatePage.genderSelectLastOption(),
      customerUpdatePage.setAddressLine1Input('addressLine1'),
      customerUpdatePage.setAddressLine2Input('addressLine2'),
      customerUpdatePage.setAddressLine3Input('addressLine3'),
      customerUpdatePage.setAddressLine4Input('addressLine4'),
      customerUpdatePage.setCityInput('city'),
      customerUpdatePage.setStateInput('state'),
      customerUpdatePage.setCountryInput('country'),
      customerUpdatePage.setZipInput('zip'),
      customerUpdatePage.userSelectLastOption(),
    ]);

    expect(await customerUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await customerUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber', 'Expected PhoneNumber value to be equals to phoneNumber');
    expect(await customerUpdatePage.getAddressLine1Input()).to.eq(
      'addressLine1',
      'Expected AddressLine1 value to be equals to addressLine1'
    );
    expect(await customerUpdatePage.getAddressLine2Input()).to.eq(
      'addressLine2',
      'Expected AddressLine2 value to be equals to addressLine2'
    );
    expect(await customerUpdatePage.getAddressLine3Input()).to.eq(
      'addressLine3',
      'Expected AddressLine3 value to be equals to addressLine3'
    );
    expect(await customerUpdatePage.getAddressLine4Input()).to.eq(
      'addressLine4',
      'Expected AddressLine4 value to be equals to addressLine4'
    );
    expect(await customerUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await customerUpdatePage.getStateInput()).to.eq('state', 'Expected State value to be equals to state');
    expect(await customerUpdatePage.getCountryInput()).to.eq('country', 'Expected Country value to be equals to country');
    expect(await customerUpdatePage.getZipInput()).to.eq('zip', 'Expected Zip value to be equals to zip');

    await customerUpdatePage.save();
    expect(await customerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Customer', async () => {
    const nbButtonsBeforeDelete = await customerComponentsPage.countDeleteButtons();
    await customerComponentsPage.clickOnLastDeleteButton();

    customerDeleteDialog = new CustomerDeleteDialog();
    expect(await customerDeleteDialog.getDialogTitle()).to.eq('webStoreApp.customer.delete.question');
    await customerDeleteDialog.clickOnConfirmButton();

    expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
