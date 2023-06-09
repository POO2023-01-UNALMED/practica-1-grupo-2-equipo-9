from datetime import date
from .metas import Metas
from .ahorros import Ahorros
from .corriente import Corriente
from .cuenta import Cuenta
from .categoria import Categoria

class Movimientos():

    # Atributos de clase para la funiconalidad Asesoramiento de inversiones
    _owner = None 
    _nombreCategoria = None 
    _cantidadCategoria = 0.0 
    _recomendarFecha = None

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