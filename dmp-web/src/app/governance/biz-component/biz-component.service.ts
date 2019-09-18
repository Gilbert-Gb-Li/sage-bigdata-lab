import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {BaseService} from '../../BaseService';

// @ts-ignore
@Injectable({
  providedIn: 'root'
})
export class BizComponentService extends BaseService {
  private url = 'api/component';

  constructor(private http: HttpClient) {
    super();
  }

  getPager(current: number = 1, size: number = 10): Observable<BizComponent[]> {
    return this.http.get<BizComponent[]>(this.url)
      .pipe(catchError(this.handleError));
  }
}

export class BizComponent {
  id: number;
  name: string;
  description: string;
  config: string;
  createTime: string;
  modifyTime: string;
}

