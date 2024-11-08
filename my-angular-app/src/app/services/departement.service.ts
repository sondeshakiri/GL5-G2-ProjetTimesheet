import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Departement } from '../models/departement';
import { DepartementDel } from '../models/departementdel';

@Injectable({
  providedIn: 'root'
})
export class DepartementService {
  //private apiUrl = 'http://localhost:8085/SpringMVC/servlet/departements';
  private apiUrl = 'http://ab08390725c3b41389b40324ea311919-1962932689.us-east-1.elb.amazonaws.com:8085/SpringMVC/servlet/departements';

  readonly ENDPONT_DEPARTEMENT = "/all";
  readonly ENDPONT_CREATE_DEPARTEMENT = "/create"; // Endpoint for creating a department
  readonly ENDPONT_DELETE_DEPARTEMENT = '/delete'; // Endpoint for deleting a department
  readonly ENDPONT_UPDATE_DEPARTEMENT = '/update';

  constructor(private httpClient: HttpClient) { }

  // Method to fetch all departments
  getAllDepartements(): Observable<Departement[]> {
    return this.httpClient.get<Departement[]>(this.apiUrl + this.ENDPONT_DEPARTEMENT).pipe(
      catchError(this.handleError) // Handle errors
    );
  }

  // Method to create a new department
  createDepartement(departement: Departement): Observable<Departement> {
    return this.httpClient.post<Departement>(this.apiUrl + this.ENDPONT_CREATE_DEPARTEMENT, departement).pipe(
      catchError(this.handleError) // Handle errors
    );
  }

  // Method to delete a department by ID
  deleteDepartement(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}${this.ENDPONT_DELETE_DEPARTEMENT}/${id}`).pipe(
      catchError(this.handleError) // Handle errors
    );
  }

  // Update an existing department
  updateDepartement(id: number, departement: DepartementDel): Observable<DepartementDel> {
    return this.httpClient.put<DepartementDel>(`${this.apiUrl}${this.ENDPONT_UPDATE_DEPARTEMENT}/${id}`, departement).pipe(
      catchError(this.handleError) // Handle errors
    );
  }

  // Error handling method
  private handleError(error: HttpErrorResponse) {
    // Handle the error according to your application's requirements
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }
}
