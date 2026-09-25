import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IServicioCritico, NewServicioCritico } from '../servicio-critico.model';

export type PartialUpdateServicioCritico = Partial<IServicioCritico> & Pick<IServicioCritico, 'id'>;

type RestOf<T extends IServicioCritico | NewServicioCritico> = Omit<T, 'volvioCriticoEn'> & {
  volvioCriticoEn?: string | null;
};

export type RestServicioCritico = RestOf<IServicioCritico>;

export type NewRestServicioCritico = RestOf<NewServicioCritico>;

export type PartialUpdateRestServicioCritico = RestOf<PartialUpdateServicioCritico>;

@Injectable()
export class ServicioCriticosService {
  readonly servicioCriticosParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly servicioCriticosResource = httpResource<RestServicioCritico[]>(() => {
    const params = this.servicioCriticosParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of servicioCritico that have been fetched. It is updated when the servicioCriticosResource emits a new value.
   * In case of error while fetching the servicioCriticos, the signal is set to an empty array.
   */
  readonly servicioCriticos = computed(() =>
    (this.servicioCriticosResource.hasValue() ? this.servicioCriticosResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/servicio-criticos');

  protected convertValueFromServer(restServicioCritico: RestServicioCritico): IServicioCritico {
    return {
      ...restServicioCritico,
      volvioCriticoEn: restServicioCritico.volvioCriticoEn ? dayjs(restServicioCritico.volvioCriticoEn) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class ServicioCriticoService extends ServicioCriticosService {
  protected readonly http = inject(HttpClient);

  create(servicioCritico: NewServicioCritico): Observable<IServicioCritico> {
    const copy = this.convertValueFromClient(servicioCritico);
    return this.http.post<RestServicioCritico>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(servicioCritico: IServicioCritico): Observable<IServicioCritico> {
    const copy = this.convertValueFromClient(servicioCritico);
    return this.http
      .put<RestServicioCritico>(`${this.resourceUrl}/${encodeURIComponent(this.getServicioCriticoIdentifier(servicioCritico))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(servicioCritico: PartialUpdateServicioCritico): Observable<IServicioCritico> {
    const copy = this.convertValueFromClient(servicioCritico);
    return this.http
      .patch<RestServicioCritico>(`${this.resourceUrl}/${encodeURIComponent(this.getServicioCriticoIdentifier(servicioCritico))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IServicioCritico> {
    return this.http
      .get<RestServicioCritico>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IServicioCritico[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestServicioCritico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getServicioCriticoIdentifier(servicioCritico: Pick<IServicioCritico, 'id'>): number {
    return servicioCritico.id;
  }

  compareServicioCritico(o1: Pick<IServicioCritico, 'id'> | null, o2: Pick<IServicioCritico, 'id'> | null): boolean {
    return o1 && o2 ? this.getServicioCriticoIdentifier(o1) === this.getServicioCriticoIdentifier(o2) : o1 === o2;
  }

  addServicioCriticoToCollectionIfMissing<Type extends Pick<IServicioCritico, 'id'>>(
    servicioCriticoCollection: Type[],
    ...servicioCriticosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const servicioCriticos: Type[] = servicioCriticosToCheck.filter(isPresent);
    if (servicioCriticos.length > 0) {
      const servicioCriticoCollectionIdentifiers = servicioCriticoCollection.map(servicioCriticoItem =>
        this.getServicioCriticoIdentifier(servicioCriticoItem),
      );
      const servicioCriticosToAdd = servicioCriticos.filter(servicioCriticoItem => {
        const servicioCriticoIdentifier = this.getServicioCriticoIdentifier(servicioCriticoItem);
        if (servicioCriticoCollectionIdentifiers.includes(servicioCriticoIdentifier)) {
          return false;
        }
        servicioCriticoCollectionIdentifiers.push(servicioCriticoIdentifier);
        return true;
      });
      return [...servicioCriticosToAdd, ...servicioCriticoCollection];
    }
    return servicioCriticoCollection;
  }

  protected convertValueFromClient<T extends IServicioCritico | NewServicioCritico | PartialUpdateServicioCritico>(
    servicioCritico: T,
  ): RestOf<T> {
    return {
      ...servicioCritico,
      volvioCriticoEn: servicioCritico.volvioCriticoEn?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestServicioCritico): IServicioCritico {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestServicioCritico[]): IServicioCritico[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
