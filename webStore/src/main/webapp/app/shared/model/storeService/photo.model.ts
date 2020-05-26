export interface IPhoto {
  id?: number;
  photoContentType?: string;
  photo?: any;
  productName?: string;
  productId?: number;
}

export class Photo implements IPhoto {
  constructor(
    public id?: number,
    public photoContentType?: string,
    public photo?: any,
    public productName?: string,
    public productId?: number
  ) {}
}
