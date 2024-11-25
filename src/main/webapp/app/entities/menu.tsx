import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/persona">
        <Translate contentKey="global.menu.entities.persona" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/empleado">
        <Translate contentKey="global.menu.entities.empleado" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cita">
        <Translate contentKey="global.menu.entities.cita" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/servicios">
        <Translate contentKey="global.menu.entities.servicios" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/establecimiento">
        <Translate contentKey="global.menu.entities.establecimiento" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto">
        <Translate contentKey="global.menu.entities.producto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/categoria-producto">
        <Translate contentKey="global.menu.entities.categoriaProducto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/galeria-imagen">
        <Translate contentKey="global.menu.entities.galeriaImagen" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pago">
        <Translate contentKey="global.menu.entities.pago" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
