export class BaseComponent {
  protected loading = false;

}

export class ListComponent extends BaseComponent {
  protected pageIndex = 1;
  protected pageSize = 10;
}
