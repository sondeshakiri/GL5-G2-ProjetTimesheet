import { Component, OnInit } from '@angular/core';
import { DepartementService } from '../services/departement.service';
import { Departement } from '../models/departement';
import { CommonModule } from '@angular/common'; // Import CommonModule
import { FormsModule } from '@angular/forms'; // Importation de FormsModule
import { DepartementDel } from '../models/departementdel';


@Component({
  selector: 'app-departement',
  standalone:true,
  imports: [CommonModule,FormsModule], // Import CommonModule here
  templateUrl: './departement.component.html',
  styleUrls: ['./departement.component.css']
})
export class DepartementComponent implements OnInit {
  departements: Departement[] = [];
  newDepartement: Departement = { name: '' };
  selectedDepartement: DepartementDel | null = null; // Pour stocker le département à modifier
  showAddForm: boolean = false;

  constructor(private departementService: DepartementService) {}

  ngOnInit(): void {
    this.fetchDepartements();
  }

  fetchDepartements(): void {
    this.departementService.getAllDepartements().subscribe(
      (data) => {
        this.departements = data;
      },
      (error) => {
        console.error('Error fetching departments:', error);
      }
    );
  }


 
  onSubmit() {
    const newDepartementToSend: Departement = {
        name: this.newDepartement.name,
        // Ne pas inclure 'entreprise' et 'id', car ils ne sont pas nécessaires
    };

    this.departementService.createDepartement(newDepartementToSend).subscribe(
        (response) => {
            console.log('Département créé', response);
            this.resetForm(); // Réinitialise le formulaire après l'ajout
            this.fetchDepartements(); // Met à jour la liste des départements
        },
        (error) => {
            console.error('Erreur lors de la création du département:', error);
            alert('Erreur lors de la création du département: ' + error.message);
        }
    );
}
deleteDepartementByName(name: string): void {
  const departementDel = this.departements.find(d => d.name === name) as DepartementDel; // Assurez-vous que vous castiez en DepartementDel
  if (departementDel && departementDel.id) { // Vérifiez que l'objet et l'ID existent
      const id = departementDel.id; // Obtenez l'ID du département
      if (confirm('Êtes-vous sûr de vouloir supprimer ce département ?')) {
          this.departementService.deleteDepartement(id).subscribe(
              () => {
                  console.log('Département supprimé');
                  this.fetchDepartements(); // Met à jour la liste après suppression
              },
              (error) => {
                  console.error('Erreur lors de la suppression du département:', error);
                  alert('Erreur lors de la suppression du département: ' + error.message);
              }
          );
      }
  } else {
      alert('Département non trouvé');
  }
}
resetForm(): void {
  this.newDepartement = { name: '' }; // Réinitialiser le formulaire
  this.selectedDepartement = null; // Réinitialiser la sélection
  this.showAddForm = false; // Masquer le formulaire d'ajout
}

  editDepartement(name: string): void {
    const departementToEdit = this.departements.find(d => d.name === name) as DepartementDel; // Cast en DepartementDel
    if (departementToEdit && departementToEdit.id) { // Vérifiez que l'objet et l'ID existent
        this.selectedDepartement = { ...departementToEdit }; // Clone l'objet pour éviter les mutations
    } else {
        alert('Département non trouvé');
    }
}
toggleAddForm(): void {
  this.showAddForm = !this.showAddForm;

}

  updateDepartement(): void {
    if (this.selectedDepartement) {
      this.departementService.updateDepartement(this.selectedDepartement.id, this.selectedDepartement).subscribe(
        (response) => {
          console.log('Département mis à jour', response);
          this.fetchDepartements();
          this.selectedDepartement = null;
        },
        (error) => {
          console.error('Erreur lors de la mise à jour du département:', error);
          alert('Erreur lors de la mise à jour du département: ' + error.message);
        }
      );
    }
  }
}
