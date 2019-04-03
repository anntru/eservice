export interface IDescription {
    id?: number;
    parameter?: string;
    itemId?: number;
}

export class Description implements IDescription {
    constructor(public id?: number, public parameter?: string, public itemId?: number) {}
}
