import { IDescription } from 'app/shared/model/description.model';

export const enum Category {
    PHONE = 'PHONE',
    COMPUTER = 'COMPUTER',
    TV = 'TV',
    PS = 'PS',
    CAMERA = 'CAMERA'
}

export const enum Status {
    WORKING = 'WORKING',
    BROKEN = 'BROKEN'
}

export interface IItem {
    id?: number;
    name?: string;
    category?: Category;
    status?: Status;
    comment?: string;
    descriptions?: IDescription[];
}

export class Item implements IItem {
    constructor(
        public id?: number,
        public name?: string,
        public category?: Category,
        public status?: Status,
        public comment?: string,
        public descriptions?: IDescription[]
    ) {}
}
