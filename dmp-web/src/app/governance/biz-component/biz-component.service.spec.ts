import { TestBed } from '@angular/core/testing';

import { BizComponentService } from './biz-component.service';

describe('BizComponentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BizComponentService = TestBed.get(BizComponentService);
    expect(service).toBeTruthy();
  });
});
