import {Injectable} from '@angular/core';
import {BaseService} from '../../BaseService';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class StorageService extends BaseService {
  private url = 'api/storage';

  constructor(private http: HttpClient) {
    super();
  }

  getPager(current: number = 1, size: number = 10): Observable<Storage[]> {
    return this.http.get<Storage[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  getByAppId(id: number): Observable<Storage[]> {
    return this.http.get<Storage[]>(this.url + '?query=')
      .pipe(catchError(this.handleError));
  }

  getById(id: number): Observable<Storage> {
    return this.http.get<Storage>(this.url + '/' + id + '?query=')
      .pipe(catchError(this.handleError));
  }
}

// status: 是否废弃
export class Storage {
  id: number;
  name: string;
  description: string;
  stage: string;
  appId: number;
  componentId: number;
  createTime: string;
  modifyTime: string;

}

