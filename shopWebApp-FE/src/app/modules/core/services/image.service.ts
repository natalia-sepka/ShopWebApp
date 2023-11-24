import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {
  HttpClient,
  HttpEvent,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { map, Observable } from 'rxjs';
import {
  DeleteImageResponse,
  Image,
  PostImageResponse,
} from '../models/image.model';
import { AngularEditorConfig, UploadResponse } from '@kolkov/angular-editor';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  apiUrl = `${environment.apiUrl}/image`;

  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Write your text here...',
    translate: 'no',
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
    sanitize: false,
    toolbarHiddenButtons: [['insertVideo']],
    customClasses: [
      {
        name: 'quote',
        class: 'quote',
      },
      {
        name: 'redText',
        class: 'redText',
      },
      {
        name: 'titleText',
        class: 'titleText',
        tag: 'h1',
      },
    ],
    uploadWithCredentials: true,
    uploadUrl: `${this.apiUrl}`,
    upload: (file: File) => {
      return this.uploadImage(file);
    },
  };

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

  uploadImage(file: File): Observable<HttpEvent<UploadResponse>> {
    const formData = new FormData();
    formData.append('multipartFile', file);
    return this.http
      .post<PostImageResponse>(`${this.apiUrl}`, formData, {
        observe: 'events',
      })
      .pipe(
        map((event) => {
          if (event instanceof HttpResponse) {
            // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
            const response: PostImageResponse = event.body!;
            const uploadResponse: UploadResponse = {
              imageUrl: `${this.apiUrl}?uuid=${response.uuid}`,
            };
            return new HttpResponse<UploadResponse>({
              ...event,
              headers: event.headers,
              status: event.status,
              statusText: event.statusText,
              url: event.url || undefined, //ustawienie undfined, jeśli url jest null
              body: uploadResponse,
            });
          }
          return event;
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
}
