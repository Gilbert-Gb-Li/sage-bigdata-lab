import {Injectable} from '@angular/core';
import {BaseService} from '../../BaseService';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SchemaService extends BaseService {
  private url = 'api/schema';

  constructor(private http: HttpClient) {
    super();
  }

  getPager(current: number = 1, size: number = 10): Observable<Schema[]> {
    return this.http.get<Schema[]>(this.url)
      .pipe(catchError(this.handleError));
  }
}

export class Schema {
  id: number;
  tipsName: string;
  saveName: string;
  description: string;
  createTime: string;
  modifyTime: string;
}

