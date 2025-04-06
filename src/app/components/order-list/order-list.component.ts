import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-list',
  imports: [],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.css'
})
export class OrderListComponent implements OnInit {
  orders: any[] = [];
  cargando: boolean = true;
  error: string = '';

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.getOrders().subscribe({
      next: (data) => {
        this.orders = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener ordenes.', err);
        this.error = 'No se pudieron cargar las ordenes.';
        this.cargando = false;
      }
    });
  }
}
