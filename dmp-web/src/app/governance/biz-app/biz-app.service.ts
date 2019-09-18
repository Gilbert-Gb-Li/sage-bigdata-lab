import {Injectable} from '@angular/core';
import {BaseService} from '../../BaseService';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BizAppService extends BaseService {
  private url = 'api/app';

  constructor(private http: HttpClient) {
    super();
  }

  getAll(): Observable<BizApp[]> {
    return this.http.get<BizApp[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  getPager(current: number = 1, size: number = 10): Observable<BizApp[]> {
    return this.http.get<BizApp[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  getById(id: number): Observable<BizApp> {
    return this.http.get<BizApp>(this.url + '/' + id + '?query=').pipe(catchError(this.handleError));
  }

  add(entity: BizApp): Observable<number> {
    console.log('json：' + JSON.stringify(entity));
    return this.http.post<number>(this.url, JSON.stringify(entity), this.options).pipe(catchError(this.handleError));
  }
}

export class BizApp {
  id: number;
  tipsName: string;
  saveName: string;
  description: string;
  createTime: string;
  modifyTime: string;
}
