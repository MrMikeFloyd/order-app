import { element, by, ElementFinder } from 'protractor';

export class CustomerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-customer div table .btn-danger'));
  title = element.all(by.css('jhi-customer div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class CustomerUpdatePage {
  pageTitle = element(by.id('jhi-customer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  genderSelect = element(by.id('field_gender'));
  addressLine1Input = element(by.id('field_addressLine1'));
  addressLine2Input = element(by.id('field_addressLine2'));
  addressLine3Input = element(by.id('field_addressLine3'));
  addressLine4Input = element(by.id('field_addressLine4'));
  cityInput = element(by.id('field_city'));
  stateInput = element(by.id('field_state'));
  countryInput = element(by.id('field_country'));
  zipInput = element(by.id('field_zip'));

  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber: string): Promise<void> {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput(): Promise<string> {
    return await this.phoneNumberInput.getAttribute('value');
  }

  async setGenderSelect(gender: string): Promise<void> {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect(): Promise<string> {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(): Promise<void> {
    await this.genderSelect.all(by.tagName('option')).last().click();
  }

  async setAddressLine1Input(addressLine1: string): Promise<void> {
    await this.addressLine1Input.sendKeys(addressLine1);
  }

  async getAddressLine1Input(): Promise<string> {
    return await this.addressLine1Input.getAttribute('value');
  }

  async setAddressLine2Input(addressLine2: string): Promise<void> {
    await this.addressLine2Input.sendKeys(addressLine2);
  }

  async getAddressLine2Input(): Promise<string> {
    return await this.addressLine2Input.getAttribute('value');
  }

  async setAddressLine3Input(addressLine3: string): Promise<void> {
    await this.addressLine3Input.sendKeys(addressLine3);
  }

  async getAddressLine3Input(): Promise<string> {
    return await this.addressLine3Input.getAttribute('value');
  }

  async setAddressLine4Input(addressLine4: string): Promise<void> {
    await this.addressLine4Input.sendKeys(addressLine4);
  }

  async getAddressLine4Input(): Promise<string> {
    return await this.addressLine4Input.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setStateInput(state: string): Promise<void> {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput(): Promise<string> {
    return await this.stateInput.getAttribute('value');
  }

  async setCountryInput(country: string): Promise<void> {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput(): Promise<string> {
    return await this.countryInput.getAttribute('value');
  }

  async setZipInput(zip: string): Promise<void> {
    await this.zipInput.sendKeys(zip);
  }

  async getZipInput(): Promise<string> {
    return await this.zipInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CustomerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-customer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-customer'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
