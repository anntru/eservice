import { element, by, ElementFinder } from 'protractor';

export class DescriptionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-description div table .btn-danger'));
    title = element.all(by.css('jhi-description div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class DescriptionUpdatePage {
    pageTitle = element(by.id('jhi-description-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    parameterInput = element(by.id('field_parameter'));
    itemSelect = element(by.id('field_item'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setParameterInput(parameter) {
        await this.parameterInput.sendKeys(parameter);
    }

    async getParameterInput() {
        return this.parameterInput.getAttribute('value');
    }

    async itemSelectLastOption() {
        await this.itemSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async itemSelectOption(option) {
        await this.itemSelect.sendKeys(option);
    }

    getItemSelect(): ElementFinder {
        return this.itemSelect;
    }

    async getItemSelectedOption() {
        return this.itemSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class DescriptionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-description-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-description'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
