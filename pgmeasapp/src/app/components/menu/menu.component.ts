/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { NestedTreeControl } from '@angular/cdk/tree';
import { Component, OnInit } from '@angular/core';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { NgxPermissionsService } from 'ngx-permissions';
import { AuthenticationService } from '@pgmeas-library/authentication';
import { UserInfo } from '@pgmeas-library/model';
import { UserService } from 'src/app/services/user.service';

interface MenuNode {
  name: string;
  secondaryText?: string;
  icon?: string;
  link?: string[];
  children?: MenuNode[];
  roles?: string[];
}

const OP_INSERISCIINTERVENTO = 'OP-InsIntervento';

const TREE_DATA: MenuNode[] = [
  {
    name: 'Home',
    icon: 'home',
    link: ['/', 'home']
  },
  {
    name: 'Ricerca interventi',
    icon: 'event',
    link: ['/', 'ricerca-interventi'],
    roles: ['OP-RicercaIntervento']
  },
  {
    name: 'Programmazione',
    /* secondaryText: ' 01/01/2024 - 31/03/2024', */
    icon: 'event',
    link: ['/','programmazione-html'],
    children: [
      {
        name: 'Gestisci programmazione',
        link: ['/', 'gestisci-programmazione'],
        roles: ['OP-RegDefProg'],
      },
      {
        name: 'Inserisci intervento',
        link: ['/', 'inserisci-intervento'],
        roles: ['OP-InsIntervento'],
      },
      {
        name: 'Consulta intervento',
        link: ['/', 'consultazione-interventi'],
        roles: ['OP-RicercaIntervento']
      }
    ]
  },
  {
    name: 'Gestione',
    icon: 'settings_applications',
    link: ['/','gestione-html'],
    children: [

      {
        name: 'Ammissione finanziamento',
        link: ['/','consulta-richiesta-ammissione-finanziamento' ],
        roles: ['OP-ConsModA','OP-ModModA', 'OP-RespModA', 'OP-ApprModA']
      }
    ]
  },
  {
    name: 'Monitoraggio',
    icon: 'analytics',
    link: ['/','monitoraggio-html'],
    children: [
      {
        name: 'Inserisci dati monitoraggio',
        link: ['/', 'modulo-c'],//'stage-area', 'insmon'
        roles: ['OP-InsMonitorag']
      },
      {
        name: 'Consulta monitoraggio',
        link: ['/', 'consulta-monitoraggio'],//'stage-area', 'consmon'
        roles: ['OP-ConsMonitorag']
      }
    ]
  },
  // {
  //   name: 'Ricerca storico interventi',
  //   icon: 'inventory_2',
  //   link: ['/', 'ricerca-storico-interventi'],
  //   roles: ['OP-RicercaIntervento']
  // }
];

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  treeControl = new NestedTreeControl<MenuNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<MenuNode>();

  constructor(private permissions: NgxPermissionsService, private userService: UserService) {}

  async ngOnInit() {
    this.dataSource.data = await this.siftNodes(TREE_DATA);
  }

  private async siftNodes(nodes: MenuNode[]): Promise<MenuNode[]> {
    const siftedNodes = [];
    const user = this.userService.getUser();

    for (const node of nodes) {
      if (node.children?.length) {
        node.children = await this.siftNodes(node.children);
      }

      if (
        (node.children?.length || node.link) &&
        (!node.roles || (await this.permissions.hasPermission(node.roles)))
      ) {
        siftedNodes.push(node);
      }
    }

    if (siftedNodes.length) {
      for (let i = siftedNodes.length - 1; i >= 0; i--) {
        const node = siftedNodes[i];
        if (node.roles?.includes(OP_INSERISCIINTERVENTO) && !user.programmazione.programmazioneAperta) {
          siftedNodes.splice(i, 1);
        }
      }
    }

    return siftedNodes;
  }

  hasChild = (_: number, node: MenuNode) => !!node.children && node.children.length > 0;
}
