from suscripcion import Suscripcion
class Usuario():
    #Atributos de clase
    _usuariosTotales = []

    #Constructor
    def __init__(self, _nombre, _correo, _contrasena, _suscripcion) -> None:
        #Atributos de instancia 
        Usuario._usuariosTotales.append(self)
        self.setId(len(Usuario.getUsuariosTotales()))
        if(_nombre == "" and _correo == "" and _contrasena == ""):
            self.setNombre("Pepe Morales")
            self.setCorreo("PepeMorales@mail.com")
            self.setContrasena("12345")
            self.setSuscripcion(Suscripcion.DIAMANTE)
        else:
            if(_suscripcion == ""):
                self.setSuscripcion(Suscripcion.BRONCE)
                self.setLimiteCuentas(self.getSuscripcion.getLimiteCuentas())
            else:
                self.setSuscripcion(_suscripcion)
                self.setLimiteCuentas(_suscripcion.getLimiteCuentas())
            self.setNombre(_nombre)
            self.setContrasena(_contrasena)
            self.setCorreo(_correo)

    #Métodos de clase
    @classmethod
    def verificarCredenciales(cls, _nombre, _contrasena) -> object:
        for usuario in Usuario.getUsuariosTotales():
            if(usuario.getNombre() == _nombre or usuario.getCorreo() == _nombre):
                if(usuario.getContrasena() == _contrasena):
                    return usuario
        return None
    
    #Métodos de instancia
    def verificarContadorMovimientos(self):
        self.setContadorMovimientos(len(self.getMovimientosAsociados()))
        if(self.getContadorMovimientos() - self.getContadorMovimientosAux() == 5):
            self.setContadorMovimientosAux(self.getContadorMovimientosAux() + 5)
            match self.getSuscripcion:
                case Suscripcion.DIAMANTE:
                    self.setContadorMovimientos(0)
                    return("Felicidades, has alcanzado el nivel máximo de suscripción")
                case Suscripcion.ORO:
                    self.setContadorMovimientos(0)
                    return("Felicidades, has alcanzado el nivel máximo de suscripción")
                case Suscripcion.PLATA:
                    self.setContadorMovimientos(0)
                    return("Felicidades, has alcanzado el nivel máximo de suscripción")
                case Suscripcion.BRONCE:
                    self.setContadorMovimientos(0)
                    return("Felicidades, has alcanzado el nivel máximo de suscripción")
                case _:
                    return("")
        else:
            return("Debes completar 5 movimientos para ser promovido de nivel, llevas " + str((self.getContadorMovimientos() - self.getContadorMovimientosAux())) + " movimiento(s)")
        
    def asociarBanco(self, banco) -> str:
        if(banco in Banco.getBancosTotales() and not(banco in self.getBancosAsociados())):
            self.getBancosAsociados().append(banco)
            return("El banco " + banco.getNombre() + " se ha asociado con éxito al usuario " + self.getNombre())
        else:
            return("No se encuentra el banco ó debes verificar que el banco que quieres asociar no se haya asociado antes, esta es la lista de bancos asociados: " + self.mostrarBancosAsociados())
        
    def asociarCuenta(self, cuenta) -> str:
        if(not(cuenta in self.getCuentasAsociadas)):
            cuenta.setTitular(self)
            self.getCuentasAsociadas().append(cuenta)
            if(isinstance(cuenta, Cuenta)):
                return(self.asociarCuentaAhorros(cuenta))
            else:
                if(cuenta.getCupo() == 0.0):
                    Corriente.inicializarCupo(cuenta)
                return(self.asociarCuentaCorriente(cuenta))
        else:
            return("Debes comprobar que la cuenta no haya sido asociada con anterioridad.")
    
    def asociarMeta(self, meta) -> str:
        if(meta in Metas.getMetasTotales()):
            meta.setDueno(self)
            self.getMetasAsociadas().append(meta)
            return("La meta " + meta.getNombre() + " se ha asociado con éxito al usuario " + self.getNombre())
        else:
            return("No se encuentra tu meta, debes verificar que la meta que quieres asociar exista" )
    
    def asociarMovimiento(self, movimiento) -> str:
        if(movimiento in Movimientos.getMovimientosTotales()):
            movimiento.setOwner(self)
            self.getMovimientosAsociados().append(movimiento)
            if(movimiento.getDestino() == None):
                return("El movimiento con origen " + movimiento.getOrigen().getNombre() + " ha sido asociada correctamente al usuario " + self.getNombre())
            else:
                if(not(movimiento.getDestino().getTitular() == self)):
                    movimiento.getDestino().getTitular().getMovimientosAsociados.append(movimiento)
                return("El movimiento con destino " + movimiento.getDestino().getNombre() + " ha sido asociada correctamente al usuario " + self.getNombre())
        else:
            return("No se encuentra el movimiento. Por favor asegurese de que el movimiento se haya realizado con éxito" )
    
    def asociarCuentaCorriente(self, corriente) -> str:
        if(corriente in Corriente.getCuentasCorrienteTotales()):
            self.getCuentasAhorroAsociadas().append(corriente)
            return("La cuenta corriente " + corriente.getNombre() + " ha sido asociada correctamente al usuario " + self.getNombre())
        else:
            return("Debes verificar que la cuenta no haya sido asociada antes")
        
    def asociarCuentaAhorros(self, ahorros) -> str:
        if(ahorros in Ahorros.getCuentasAhorroTotales()):
            self.getCuentasAhorroAsociadas.append(ahorros)
            return("La cuenta de ahorros " + ahorros.getNombre() + " ha sido asociada correctamente al usuario " + self.getNombre())
        else:
            return("Debes verificar que la cuenta no haya sido asociada antes")
    
    def mostrarBancosAsociados(self) -> object:
        bancos = self.getBancosAsociados()
        if(len(bancos) != 0):
            return bancos
        else:
            return("Primero debes asociar bancos")
        
    def mostrarCuentasAsociadas(self) -> object:
        cuentas = self.getCuentasAsociadas()
        if(len(cuentas) != 0):
            return cuentas
        else:
            return ("Primero debes asociar cuentas")
        
    def __str__(self) -> str:
        return("Usuario: " + self.getNombre() +
				"\nCorreo: " + self.getCorreo() +
				"\n#: " + str(self.getId()) +
				"\nCuentas Asociadas: " + str(self.getCuentasAsociadas()) +
				"\nSuscripción: " + str(self.getSuscripcion()))


    #Métodos Get & Set
    @classmethod
    def getUsuariosTotales(cls) -> list:
        return cls._usuariosTotales
    @classmethod
    def setUsuariosTotales(cls, _usuariosTotales) -> None:
        cls._usuariosTotales = _usuariosTotales
    def getNombre(self) -> str:
        return self._nombre
    def setNombre(self, _nombre) -> None:
        self._nombre = _nombre
    def getCorreo(self) -> str:
        return self._correo
    def setCorreo(self, _correo) -> None:
        self._correo = _correo
    def getContrasena(self) -> str:
        return self._contrasena
    def setContrasena(self, _contrasena) -> None:
        self._contrasena = _contrasena
    def getId(self) -> int:
        return self._id
    def setId(self, _id) -> None:
        self._id = _id
    def getSuscripcion(self) -> Suscripcion:
        return self._suscripcion
    def setSuscripcion(self, _suscripcion) -> None:
        self._suscripcion = _suscripcion
    def getCuentasAsociadas(self) -> list:
        return self._cuentasAsociadas
    def setCuentasAsociadas(self, _cuentasAsociadas) -> None:
        self._cuentasAsociadas = _cuentasAsociadas
    def getLimiteCuentas(self) -> int:
        return self._limiteCuentas
    def setLimiteCuentas(self, _limiteCuentas) -> None:
        self._limiteCuentas = _limiteCuentas
    def getBancosAsociados(self) -> list:
        return self._bancosAsociados
    def setBancosAsociados(self, _bancosAsociados) -> None:
        self._bancosAsociados = _bancosAsociados
    def getContadorMovimientos(self) -> int:
        return self._contadorMovimientos
    def setContadorMovimientos(self, _contadorMovimientos) -> None:
        self._contadorMovimientos = _contadorMovimientos
    def getMetasAsociadas(self) -> list:
        return self._metasAsociadas
    def setMetasAsociadas(self, _metasAsociadas) -> None:
        self._metasAsociadas = _metasAsociadas
    def getMovimientosAsociados(self) -> list:
        return self._movimientosAsociados
    def setMovimientosAsociados(self, _movimientosAsociados) -> None:
        self._movimientosAsociados = _movimientosAsociados
    def getCuentasCorrienteAsociadas(self) -> list:
        return self._cuentasCorrienteAsociadas
    def setCuentasCorrienteAsociadas(self, _cuentasCorrienteAsociadas) -> None:
        self._cuentasCorrienteAsociadas = _cuentasCorrienteAsociadas
    def getCuentasAhorroAsociadas(self) -> list:
        return self._cuentasAhorroAsociadas
    def setCuentasAhorroAsociadas(self, _cuentasAhorroAsociadas) -> None:
        self._cuentasAhorroAsociadas = _cuentasAhorroAsociadas
    def getContadorMovimientosAux(self) -> int:
        return self._contadorMovimientosAux
    def setContadorMovimientosAux(self, _contadorMovimientosAux) -> None:
        self._contadorMovimientosAux = _contadorMovimientosAux


