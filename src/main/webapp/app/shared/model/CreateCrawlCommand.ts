export interface ICreateCrawlCommand {
  urls?: string;
}

export class CreateCrawlCommand implements ICreateCrawlCommand {
  constructor(public urls?: string) {}
}
