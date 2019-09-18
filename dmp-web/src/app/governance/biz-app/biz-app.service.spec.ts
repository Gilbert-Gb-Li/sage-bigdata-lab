import { TestBed } from '@angular/core/testing';

import { BizAppService } from './biz-app.service';

describe('BizAppService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BizAppService = TestBed.get(BizAppService);
    expect(service).toBeTruthy();
  });
});
