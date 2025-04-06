import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";

import { AppComponent } from "./app.component";
import { OrderCreateComponent } from "./components/order-create/order-create.component";
import { OrderListComponent } from "./components/order-list/order-list.component";

@NgModule({
    declarations: [
        AppComponent,
        OrderCreateComponent,
        OrderListComponent
    ],
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        HttpClientModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }