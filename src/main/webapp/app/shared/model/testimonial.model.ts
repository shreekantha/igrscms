export interface ITestimonial {
  id?: number;
  name?: string;
  imgContentType?: string;
  img?: any;
  imgLink?: string;
  batchYear?: string;
  note?: any;
}

export class Testimonial implements ITestimonial {
  constructor(
    public id?: number,
    public name?: string,
    public imgContentType?: string,
    public img?: any,
    public imgLink?: string,
    public batchYear?: string,
    public note?: any
  ) {}
}
