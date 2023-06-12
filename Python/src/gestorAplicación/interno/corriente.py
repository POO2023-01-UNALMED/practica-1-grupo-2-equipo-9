from .cuenta import Cuenta
from gestorAplicación.externo.banco import Banco
from datetime import date
import math

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

    def retornoCuotaMensual(self, deudaActual, mes = 0):
        cuotaMensual = []
        interes_nominal_mensual = Corriente.calculoInteresNominalMensual(self.getIntereses())
        if mes == 0:
            interes = deudaActual * (interes_nominal_mensual / 100)
            cuotaMensual[0] = interes
            abono_capital = (self.getCupo() - self.getDisponible()) / self.getPlazo_Pago().getCantidad_Cuotas()
            cuotaMensual[1] = abono_capital
            cuotaMensualFinal = interes + abono_capital
            cuotaMensual[2] = cuotaMensualFinal
        elif mes == 1:
            cuotaMensual[0] = 0
            abono_capital = (self.getCupo() - self.getDisponible()) / self.getPlazo_Pago().getCantidad_Cuotas()
            cuotaMensualFinal = abono_capital
            cuotaMensual[2] = cuotaMensualFinal
        elif mes == 2:
            abono_capital = (self.getCupo() - self.getDisponible()) / self.getPlazo_Pago().getCantidad_Cuotas()
            interes_mes1 = (interes_nominal_mensual / 100) * (abono_capital + deudaActual)
            interes_mes2 = deudaActual * (interes_nominal_mensual / 100)
            interes = interes_mes1 + interes_mes2
            cuotaMensual[0] = interes
            cuotaMensual[1] = abono_capital
            cuotaMensualFinal = interes + abono_capital
            cuotaMensual[2] = cuotaMensualFinal
        else:
            interes = deudaActual * (interes_nominal_mensual / 100)
            cuotaMensual[0] = interes
            abono_capital = (self.getCupo() - self.getDisponible()) / self.getPlazo_Pago().getCantidad_Cuotas()
            cuotaMensual[1] = abono_capital
            cuotaMensualFinal = interes + abono_capital
            cuotaMensual[2] = cuotaMensualFinal
        return cuotaMensual

    @staticmethod
    def imprimitCuotaMensual(cls, cuotaMensual):
        return "Cuota: " + Corriente.redondeoDeciomal(cuotaMensual[2], 2) + "\nIntereses: " + Corriente.redondeoDecimal(cuotaMensual[0], 2) + "\nAbono a capital: " + Corriente.redondeoDecimal(cuotaMensual[1], 2)

    @staticmethod
    def calculoInteresNominalMensual(cls, interesEfectivoAnual):
        interes = math.pow((1 + (interesEfectivoAnual / 100)), (30.0 / 360.0)) - 1
        interes_porcentaje = interes * 100
        interes_porcentaje_redondeado = Corriente.redondeoDecimal(interes_porcentaje, 2)
        return interes_porcentaje_redondeado

    @staticmethod
    def vistaPreviaMovimiento(cls, cuenta, plazo, deuda_Previa, interes):
        cuenta_aux = cuenta.clone()
        cuenta_aux.setDisponible(cuenta.getDisponible() - deuda_Previa)
        cuenta_aux.setIntereses(interes)
        cuenta_aux.setPlazo_Pago(plazo)
        return cuenta_aux
    #Revisar necesidad del clonado

    @staticmethod
    def calculadoraCuotas(cls, cuotas, deuda, intereses, auxiliar):
        cuotasTotales = cuotas.getCantidad_Cuotas()
        cuota = []
        interesMensual = Corriente.calculoInteresNominalMensual(intereses)
        deudaActual = deuda

        abono_capital = deuda / cuotasTotales

        if not auxiliar:
            for i in range(0, i < cuotasTotales, 1):
                cuotaMes = []
                interes = deudaActual * (interesMensual / 100)
                cuotaMes[0] = interes
                cuota_pagar = interes + abono_capital
                cuotaMes[1] = cuota_pagar
                deudaTotal = deudaActual - (cuota_pagar - interes)
                cuotaMes[2] = deudaTotal
                cuota[i] = cuotaMes

                deudaActual = deudaTotal
        else:
            interesMes1 = deudaActual * (interesMensual / 100)
            cuotaMes1 = []
            cuotaMes1[0] = 0
            cuotaMes1[1] = abono_capital
            cuotaMes1[2] = deudaActual - abono_capital
            cuota[0] = cuotaMes1

            deudaActual = deudaActual - abono_capital

            for i in range(1, 1 < cuotasTotales, 1):
                cuotaMes = []
                interes = deudaActual * (interesMensual / 100)
                cuotaMes[0] = interes + interesMes1
                cuota_pagar = interes + abono_capital + interesMes1
                cuotaMes[1] = cuota_pagar
                deudaTotal = deudaActual - (cuota_pagar - (interes + interesMes1))
                cuotaMes[2] = deudaTotal
                cuota[i] = cuotaMes

                interesMes1 = 0
                deudaActual = deudaTotal
            
        return cuota
            
        

    @staticmethod
    def informacionAdicionalCalculadora(cls, cuota, deuda):
        infoAdicional = []
        totalPagado = 0

        for i in range(0, len(cuota), 1):
            totalPagado += cuota[i][1]
        
        interesesPagados = totalPagado - deuda

        totalPagado = Corriente.redondeoDecimal(totalPagado, 2)
        interesesPagados = Corriente.redondeoDecimal(interesesPagados, 2)
        deuda = Corriente.redondeoDecimal(deuda, 2)

        infoAdicional[0] = totalPagado
        infoAdicional[1] = interesesPagados
        infoAdicional[2] = deuda

        return infoAdicional

    @staticmethod
    def inicializarCupo(cls, cuenta):
        banco = cuenta.getBanco()
        suscripcion = cuenta.getTitular().getSuscripcion()
        cupo = Banco.decisionCupo(suscripcion, banco)
        cuenta.setCupo(cupo)
        cuenta.setDisponible(cupo)

    def capacidadDeuda(self, cantidad):
        if self.getDisponible().compareTo(cantidad) >= 0:
            return True
        else:
            return False

    @staticmethod
    def limpiarPropiedades(cls, arreglo):
        arreglo.remove("cuentasTotales")
        #Verificar que otras variables se crean
    #Verificar su uso

    #Realizar el método del compare to
    def compareTo(self, cuenta):
        if self.getDisponible() > cuenta.getDisponible():
            return 1
        elif self.getDisponible() < cuenta.getDisponible():
            return -1
        else:
            return 0

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