import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {IconsProviderModule} from './icons-provider.module';
import {NgZorroAntdModule, NZ_I18N, zh_CN} from 'ng-zorro-antd';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {registerLocaleData} from '@angular/common';
import zh from '@angular/common/locales/zh';

import {DemoComponent} from './governance/demo/demo.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {UserComponent} from './system/user/user.component';
import {PowerComponent} from './system/power/power.component';
import {SchemaComponent} from './governance/schema/schema.component';
import {BizComponentComponent} from './governance/biz-component/biz-component.component';
import {StorageComponent} from './governance/storage/storage.component';
import {FieldComponent} from './governance/field/field.component';
import {EnumTypeComponent} from './governance/enum/enum-type.component';
import {DataTabComponent} from './governance/data-tab/data-tab.component';
import {BizAppComponent} from './governance/biz-app/biz-app.component';
import {DataIndexComponent} from './governance/data-index/data-index.component';
import { ZenChartComponent } from './zen-chart/zen-chart.component';
import { SvgDemo1Component } from './demo/svg-demo1/svg-demo1.component';
import { LineageComponent } from './governance/lineage/lineage.component';
import { ParserComponent } from './process/parser/parser.component';
import { AnalysisComponent } from './process/analysis/analysis.component';
import { StorageEditComponent } from './governance/storage/storage-edit/storage-edit.component';
import { BizAppEditComponent } from './governance/biz-app/biz-app-edit/biz-app-edit.component';
import { SchemaEditComponent } from './governance/schema/schema-edit/schema-edit.component';
import { FieldEditComponent } from './governance/field/field-edit/field-edit.component';
import { EnumEditComponent } from './governance/enum/enum-edit/enum-edit.component';
import { BizComponentEditComponent } from './governance/biz-component/biz-component-edit/biz-component-edit.component';

registerLocaleData(zh);

@NgModule({
  declarations: [
    AppComponent,
    BizAppComponent,
    DemoComponent,
    DashboardComponent,
    UserComponent,
    PowerComponent,
    SchemaComponent,
    BizComponentComponent,
    StorageComponent,
    FieldComponent,
    EnumTypeComponent,
    DataTabComponent,
    BizAppComponent,
    DataIndexComponent,
    ZenChartComponent,
    SvgDemo1Component,
    LineageComponent,
    ParserComponent,
    AnalysisComponent,
    StorageEditComponent,
    BizAppEditComponent,
    SchemaEditComponent,
    FieldEditComponent,
    EnumEditComponent,
    BizComponentEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    IconsProviderModule,
    NgZorroAntdModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule
  ],
  providers: [{provide: NZ_I18N, useValue: zh_CN}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
