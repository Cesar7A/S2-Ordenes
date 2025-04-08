import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { OrderConfirmedPageComponent } from '../components/order-confirmed-page/order-confirmed-page.component';
const routes: Routes = [
  { path: 'order-confirmation/:id', component: OrderConfirmedPageComponent },  // Definir ruta para la página de confirmación
];

@NgModule({
  declarations: [OrderConfirmedPageComponent],  // Declarar el componente
  imports: [
    CommonModule,
    RouterModule.forChild(routes),  // Configurar rutas del módulo
  ],
  providers: [],
})
export class OrderModule {}