from .cuenta import Cuenta
from datetime import date

class Corriente(Cuenta):
    #Atributos de clase
    _cuentasCorrienteTotales = []

    #Constructor
    def __init__(self, banco, clave, divisa, nombre):
        #Atributos de instancia
        Corriente._cuentasCorrienteTotales.append(self)
        super().__init__(banco, clave, divisa, nombre)

#REVISAR SOBRECARGA

    def crearCuenta(self, banco, clave, divisa, nombre):
        return Corriente(banco, clave, divisa, nombre)
    
    def __str__(self):
        return "Cuenta: " + self._nombre + "\nCuenta Corriente # " + self._id + "\nTitular: " + self.getTitular().getNombre() + "\nBanco: " + self._banco.getNombre() + "\nDivisa: " + self._divisa + "\nCupo: " + self._cupo + " " + self._divisa + "\nCupo disponible: " + self._disponible + " " + self._divisa + "\Cuotas: " + self._plazo_Pago + "\nIntereses: " + self._intereses
    
    # Método para la funcionalidad asesoramiento de inversiones
    def vaciarCuenta(self, gota):
        from .movimientos import Movimientos
        from .categoria import Categoria
        movimiento = Movimientos(self, gota, self.getDisponible(), Categoria.OTROS, date.now())
        self.getTitular().getMovimientosAsociados().append(movimiento)
        Movimientos.getMovimientosTotales().remove(movimiento)

    def retornoCuotaMensual(self, deudaActual, args):
        pass

    @staticmethod
    def imprimitCuotaMensual(cls, cuotaMensual):
        pass

    @staticmethod
    def calculoInteresNominalMensual(cls, interesEfectivoAnual):
        pass

    @staticmethod
    def vistaPreviaMovimiento(cls, cuenta, plazo, deuda_Previa, interes):
        pass

    @staticmethod
    def calculadoraCuotas(cls, cuotas, deuda, intereses):
        pass

    @staticmethod
    def informacionAdicionalCalculadora(cls, cuota, deuda):
        pass

    @staticmethod
    def inicializarCupo(cls, cuenta):
        pass

    def capacidadDeuda(self, cantidad):
        pass

    @staticmethod
    def limpiarPropiedades(cls, arreglo):
        pass

    #Hacer el compare to

    #Revisar la parte del clone

    

    #Atributos Get & Set
    def getCupo(self):
        return self._cupo
    def setCupo(self, cupo):
        self._cupo = cupo

    def getPlazo_Pago(self):
        return self._plazo_Pago
    def setPlazo_Pago(self, plazo_Pago):
        self._plazo_Pago = plazo_Pago
    
    def getDisponible(self):
        return self._disponible
    def setDisponible(self, disponible):
        self._disponible = disponible

    def getIntereses(self):
        return self._intereses
    def setIntereses(self, intereses):
        self._intereses = intereses

    def getPrimerMensualidad(self):
        return self._primerMensualidad
    def setPrimerMensualidad(self, primerMensualidad):
        self._primerMensualidad = primerMensualidad

    @classmethod
    def getCuentasCorrienteTotales(cls):
        return cls._cuentasCorrienteTotales
    def setCuentasCorrienteTotales(cls, cuentasCorrienteTotales):
        cls._cuentasCorrienteTotales = cuentasCorrienteTotales