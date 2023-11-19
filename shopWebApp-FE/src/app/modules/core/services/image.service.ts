import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import {
  DeleteImageResponse,
  Image,
  PostImageResponse,
} from '../models/image.model';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  apiUrl = `${environment.apiUrl}/image`;
  constructor(private http: HttpClient) {}

  addImage(formData: FormData): Observable<Image> {
    return this.http
      .post<PostImageResponse>(`${this.apiUrl}`, formData, {
        withCredentials: true,
      })
      .pipe(
        map((response) => {
          return { url: `${this.apiUrl}?uuid=${response.uuid}` };
        }),
      );
  }

  deleteImage(uuid: string): Observable<DeleteImageResponse> {
    const params = new HttpParams().append('uuid', uuid);
    return this.http.delete<DeleteImageResponse>(`${this.apiUrl}`, {
      withCredentials: true,
      params,
    });
  }

  getImage(uuid: string): Observable<Image> {
    return this.http.get<Image>(`${this.apiUrl}?uuid`);
  }
}
