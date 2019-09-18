import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DemoComponent} from './governance/demo/demo.component';
import {FieldComponent} from './governance/field/field.component';
import {EnumTypeComponent} from './governance/enum/enum-type.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {BizAppComponent} from './governance/biz-app/biz-app.component';
import {SchemaComponent} from './governance/schema/schema.component';
import {DataIndexComponent} from './governance/data-index/data-index.component';
import {StorageComponent} from './governance/storage/storage.component';
import {BizComponentComponent} from './governance/biz-component/biz-component.component';
import {LineageComponent} from './governance/lineage/lineage.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: '/governance/index'},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'governance/index', component: DataIndexComponent},
  {path: 'governance/app', component: BizAppComponent},
  {path: 'governance/schema', component: SchemaComponent},
  {path: 'governance/store', component: StorageComponent},
  {path: 'governance/field', component: FieldComponent},
  {path: 'governance/enum', component: EnumTypeComponent},
  {path: 'governance/component', component: BizComponentComponent},
  {path: 'governance/lineage', component: LineageComponent},
  {path: 'demo', component: DemoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
