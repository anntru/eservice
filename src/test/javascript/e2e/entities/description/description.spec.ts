/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DescriptionComponentsPage, DescriptionDeleteDialog, DescriptionUpdatePage } from './description.page-object';

const expect = chai.expect;

describe('Description e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let descriptionUpdatePage: DescriptionUpdatePage;
    let descriptionComponentsPage: DescriptionComponentsPage;
    let descriptionDeleteDialog: DescriptionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Descriptions', async () => {
        await navBarPage.goToEntity('description');
        descriptionComponentsPage = new DescriptionComponentsPage();
        await browser.wait(ec.visibilityOf(descriptionComponentsPage.title), 5000);
        expect(await descriptionComponentsPage.getTitle()).to.eq('Descriptions');
    });

    it('should load create Description page', async () => {
        await descriptionComponentsPage.clickOnCreateButton();
        descriptionUpdatePage = new DescriptionUpdatePage();
        expect(await descriptionUpdatePage.getPageTitle()).to.eq('Create or edit a Description');
        await descriptionUpdatePage.cancel();
    });

    it('should create and save Descriptions', async () => {
        const nbButtonsBeforeCreate = await descriptionComponentsPage.countDeleteButtons();

        await descriptionComponentsPage.clickOnCreateButton();
        await promise.all([descriptionUpdatePage.setParameterInput('parameter'), descriptionUpdatePage.itemSelectLastOption()]);
        expect(await descriptionUpdatePage.getParameterInput()).to.eq('parameter');
        await descriptionUpdatePage.save();
        expect(await descriptionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await descriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Description', async () => {
        const nbButtonsBeforeDelete = await descriptionComponentsPage.countDeleteButtons();
        await descriptionComponentsPage.clickOnLastDeleteButton();

        descriptionDeleteDialog = new DescriptionDeleteDialog();
        expect(await descriptionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Description?');
        await descriptionDeleteDialog.clickOnConfirmButton();

        expect(await descriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
