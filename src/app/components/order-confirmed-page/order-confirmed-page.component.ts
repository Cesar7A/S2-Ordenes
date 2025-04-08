// order-confirmed-page.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from '../../services/order.service'; 

@Component({
  selector: 'app-order-confirmed-page',
  templateUrl: './order-confirmed-page.component.html',
  styleUrls: ['./order-confirmed-page.component.scss']
})
export class OrderConfirmedPageComponent implements OnInit {
  order: any;

  constructor(private orderService: OrderService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const orderId = this.route.snapshot.paramMap.get('id'); // Obtén el ID de la orden desde la URL
    if (orderId) {
      this.orderService.getOrderDetails(orderId).subscribe((data) => {
        this.order = data; // Asigna los detalles de la orden al componente
      });
    }
  }

  goToHome() {
    // Redirige al usuario al inicio
    window.location.href = '/';
  }

  trackOrder() {
    // Lógica para rastrear la orden
    window.location.href = `/track/${this.order.orderId}`;
  }
}