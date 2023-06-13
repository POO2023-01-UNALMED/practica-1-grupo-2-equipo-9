from datetime import date
from .metas import Metas
from .ahorros import Ahorros
from .corriente import Corriente
from .cuenta import Cuenta
from .categoria import Categoria
from .deuda import Deuda
from gestorAplicación.externo.banco import Banco
from gestorAplicación.externo.divisas import Divisas

class Movimientos():
    _movimientosTotales = []
    def __init__(self,**kwargs):
        Movimientos._movimientosTotales.append(self)
        self._id = len(Movimientos._movimientosTotales)
        self._cantidad = None
        self._categoria = None
        self._fecha = None
        self._destino = None
        self._origen = None
        self._divisa = None
        self._divisaAux = None
        self._banco = None
        self._cuotaManejo = None
    # Atributos de clase para la funiconalidad Asesoramiento de inversiones

        self._owner= None
        self._nombreCategoria = None
        self._cantidadCategoria = 0.0
        self._recomendarFecha = None

        for key in kwargs:
            if key == "cantidad":
                self._cantidad = kwargs[key]
            if key == "categoria":
                self._categoria = kwargs[key]
            if key == "fecha":
                self._fecha = kwargs[key]
            if key == "destino":
                self._destino = kwargs[key]
            if key == "origen":
                self._origen = kwargs[key]
            if key == "divisa":
                self._divisa = kwargs[key]
            if key == "divisaAux":
                self._divisaAux= kwargs[key]
            if key == "banco":
                self._banco = kwargs[key]
            if key == "cuotaManejo":
                self._cuotaManejo = kwargs[key]
            if key == "owner":
                self._owner = kwargs[key]
            if key == "nombreCategoria":
                self._nombreCategoria = kwargs[key]
            if key == 'cantidadCategoria':
                self._cantidadCategoria = kwargs[key]
            if key == "recomendarFecha":
                self._recomendarFecha = kwargs[key]
            
    # Métodos de la funiconalidad Asesoramiento de inversiones
    def analizarCategoria(self, user, plazo): 
        transporte = 0 
        comida = 0 
        educacion = 0 
        finanzas = 0 
        otros = 0 
        regalos = 0 
        salud = 0 
        prestamos = 0 
        big = 0 
        posicion = 0 
        mayor = []

        # Buscar la categoría en la que más dinero ha gastado el usuario
        for i in range(len(user.getMovimientosAsociados())):
            categoria = user.getMovimientosAsociados()[i].getCategoria()
            if categoria == Categoria.TRANSPORTE:
                transporte += 1
            elif categoria == Categoria.COMIDA:
                comida += 1
            elif categoria == Categoria.EDUCACION:
                educacion += 1
            elif categoria == Categoria.SALUD:
                salud += 1
            elif categoria == Categoria.REGALOS:
                regalos += 1
            elif categoria == Categoria.FINANZAS:
                finanzas += 1
            elif categoria == Categoria.OTROS:
                otros += 1
            elif categoria == Categoria.PRESTAMO:
                prestamos += 1

        mayor.extend([transporte, comida, educacion, salud, regalos, finanzas, otros, prestamos])

        for e in range(len(mayor)):
            if mayor[e] > big:
                big = mayor[e]
                posicion = e

        if posicion == 0:
            self.self.setNombreCategoria("Transporte")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.TRANSPORTE == user.getMovimientosAsociados()[i].getCategoria():
                    cantidadCategoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 1:
            self.setNombreCategoria("Comida")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.COMIDA == user.getMovimientosAsociados()[i].getCategoria():
                    cantidadCategoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 2:
            self.setNombreCategoria("Educacion")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.EDUCACION == user.getMovimientosAsociados()[i].getCategoria():
                    cantidadCategoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 3:
            self.setNombreCategoria("Salud")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.SALUD == user.getMovimientosAsociados()[i].getCategoria():
                    cantidadCategoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 4:
            self.setNombreCategoria("Regalos")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.REGALOS == user.getMovimientosAsociados()[i].getCategoria():
                    cantidadCategoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 5:
            self.setNombreCategoria("Finanzas")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.FINANZAS == user.getMovimientosAsociados()[i].getCategoria():
                    cantidadCategoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 6:
            self.setNombreCategoria("Otros")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.OTROS == user.getMovimientosAsociados()[i].getCategoria():
                    cantidadCategoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 7:
            self.setNombreCategoria("Prestamos")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.OTROS == user.getMovimientosAsociados()[i].getCategoria():
                    cantidadCategoria += user.getMovimientosAsociados()[i].getCantidad()

        # Recomendadar fecha
        if plazo == "Corto":
            if user.getMovimientosAsociados()[len(user.getMovimientosAsociados()) - 1].getFecha() < Metas.revisionMetas(user).getFecha():
                self.self.setRecomendarFecha("01/01/2024")
            else:
                self.setRecomendarFecha("01/06/2025")
        elif plazo == "Mediano":
            if user.getMovimientosAsociados()[len(user.getMovimientosAsociados()) - 1].getFecha() < Metas.revisionMetas(user).getFecha():
                self.setRecomendarFecha("01/01/2026")
            else:
                self.setRecomendarFecha("01/06/2027")
        elif plazo == "Largo":
            if user.getMovimientosAsociados()[len(user.getMovimientosAsociados()) - 1].getFecha() < Metas.revisionMetas(user).getFecha():
                self.setRecomendarFecha("01/01/2028")
            else:
                self.setRecomendarFecha("01/06/2029")
        else:
            self.setRecomendarFecha(None)

    def impuestosMovimiento(self, interes):
        impuestosBanco = Ahorros(self.getOrigen().getBanco(), 1234, Divisas.COP, "Ahorros", 10.0)
        if self.getOrigen().getBanco() == self.getDestino().getBanco():
            if isinstance(self.getOrigen(), Corriente):
                movimiento1 = Movimientos(self.getOrigen(), impuestosBanco, interes, Categoria.OTROS, date.now())
                Movimientos.getMovimientosTotales().remove(movimiento1)
            else:
                movimiento1 = Movimientos(self.getOrigen(), impuestosBanco, interes, Categoria.OTROS, date.now())
                Movimientos.getMovimientosTotales().remove(movimiento1)
            Ahorros.getCuentasAhorroTotales().remove(impuestosBanco)
            Cuenta.getCuentasTotales().remove(impuestosBanco)
            return True
        else:
            if isinstance(self.getOrigen(), Corriente):
                movimiento1 = Movimientos(self.getOrigen(), impuestosBanco, interes + 1, Categoria.OTROS, date.now())
                Movimientos.getMovimientosTotales().remove(movimiento1)
            else:
                movimiento1 = Movimientos(self.getOrigen(), impuestosBanco, interes + 1, Categoria.OTROS, date.now())
                Movimientos.getMovimientosTotales().remove(movimiento1)
            Ahorros.getCuentasAhorroTotales().remove(impuestosBanco)
            Cuenta.getCuentasTotales().remove(impuestosBanco)
            return False

    # Funcionlidad Prestamo
    @classmethod
    def realizarPrestamo(cls,_cuenta,_cantidad):
        banco = _cuenta.getBanco()
        titular = _cuenta.getTitular()
        maxCantidad = banco.getPrestamo() * titular.getSuscripcion().getPorcentajePrestamo()
        if(_cantidad > maxCantidad or _cantidad<=0):
            return None
        else:
            deuda = Deuda(_cantidad,_cuenta,titular,banco)
            return(Movimientos.crearMovimiento(_cuenta,_cantidad,Categoria.Prestamos,date.now()))
        
    @classmethod
    def pagarDeuda(_usuario,_deuda,_cantidad):
        if _deuda.getCantidad()==_cantidad:
            cuenta = _deuda.getCuenta()
            Deuda.getDeudasTotales().remove(_deuda)
            Metas.getMetasTotales().remove(_deuda)
            _deuda.getCantidad(0)
            cantidad= - _cantidad
            return Movimientos.crearMovimiento(cuenta,cantidad,Categoria.PRESTAMO, date.now())
        else:
            _deuda.setCantidad(_deuda.getCantidad()-_cantidad)
            cuenta = _deuda.getCantidad()
            cantidad = -_cantidad
            return Movimientos.crearMovimiento(cuenta,cantidad,Categoria.PRESTAMO,date.now())
        
    # Funcionalidad de cambio de divisa
    @classmethod
    def facilitar_informacion(mov):
        for i in range(len(mov.get_owner().get_bancos_asociados())):
            mov.get_owner().get_bancos_asociados()[i].set_asociado(True)

        cadena = mov.get_divisa().name + mov.get_divisa_aux().name
        existe_cambio = []
        for j in range(len(Banco.get_bancos_totales())):
            for k in range(len(Banco.get_bancos_totales()[j].get_dic())):
                if cadena == Banco.get_bancos_totales()[j].get_dic()[k]:
                    existe_cambio.append(Banco.get_bancos_totales()[j])

        return existe_cambio


    def getOwner(self):
        return self._owner

    def setOwner(self, owner):
        self._owner = owner

    def getNombreCategoria(self):
        return self._nombreCategoria

    def setNombreCategoria(self, nombreCategoria):
        self._nombreCategoria = nombreCategoria

    def getCantidadCategoria(self):
        return self._cantidadCategoria

    def setCantidadCategoria(self, cantidadCategoria):
        self._cantidadCategoria = cantidadCategoria

    def getRecomendarFecha(self):
        return self._recomendarFecha

    def setRecomendarFecha(self, recomendarFecha):
        self._recomendarFecha = recomendarFecha