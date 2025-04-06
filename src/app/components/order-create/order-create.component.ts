import { Component, Directive } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderService } from '../../services/order.service';


@Component({
  selector: 'app-order-create',
  imports: [],
  templateUrl: './order-create.component.html',
  styleUrl: './order-create.component.css'
})
export class OrderCreateComponent {
  orderForm: FormGroup;
  mensaje: string = '';

  constructor(
    private fb: FormBuilder,
    private orderService: OrderService
  ) {
    this.orderForm = this.fb.group({
      productoId: ['', Validators.required],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      direccion: ['', Validators.required]
    });
  }

  submitOrden() {
    if (this.orderForm.valid) {
      const datos = this.orderForm.value;

      this.orderService.crearOrden(datos).subscribe({
        next: (res) => {
          console.log('Orden creada:', res);
          this.mensaje = 'Orden creada exitosamente.';
          this.orderForm.reset(); //Limpiar formulario
        },
        error: (err) => {
          console.error('Error al crear orden:', err);
          this.mensaje = 'Error al crear la orden.';
        }
      });
    } else {
      this.mensaje = 'Por favor completa todos los campos.';
    }
  }

}
