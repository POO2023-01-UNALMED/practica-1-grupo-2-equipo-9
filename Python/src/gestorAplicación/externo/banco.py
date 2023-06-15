import random
from datetime import datetime

from gestorAplicación.interno.suscripcion import Suscripcion
from gestorAplicación.interno.categoria import Categoria
from .estado import Estado

class Banco():
    _bancosTotales = []

    def __init__(self, nombre = "Banco de Colombia", comision = 0.3, estado = Estado(), prestamo = 200, **kwargs):
        #Revisar ingreso de atributo Estado, para que su defecto sea el primero como en Java
        self._nombre = nombre
        self._comision = comision
        self._estado = estado
        self._prestamo = prestamo

        self._cupo_base = 1000000
        self._multiplicador = 2
        self._desc_suscripcion = 0.2
        self._desc_movimientos_porcentaje = 0.2
        self._desc_movimientos_cantidad = 5

        #Atributos de instancia
        Banco._bancosTotales.append(self)
        self._id = len(Banco._bancosTotales)
        for key in kwargs:
            if key == "desc_suscripcion":
                self._desc_suscripcion = kwargs[key]
            if key == "desc_movimientos_porcentaje":
                self._desc_movimientos_porcentaje = kwargs[key]
            if key == "desc_movimientos_cantidad":
                self._desc_movimientos_cantidad = kwargs[key]
            if key == "divisa":
                self._divisa = kwargs[key]
            if key == "dic":
                self._dic = kwargs[key]
            if key == "cionario":
                self._cionario = kwargs[key]
            if key == "cupo_base":
                self._cupo_base
            if key == "multiplicador":
                self._multiplicador
            if key == "desc_suscripcion":
                self._desc_suscripcion
            if key == "desc_movimientos_porcentaje":
                self._desc_movimientos_porcentaje
            if key == "desc_movimientos_cantidad":
                self._desc_movimientos_cantidad

    #Métodos de la funcionalidad de cambio de divisa
    @staticmethod
    def cotizar_taza(user, existe_cambio, cadena, ahorros_posibles):
        from gestorAplicación.interno.movimientos import Movimientos
        imprimir = []
        for ahorro in ahorros_posibles:
            for banco in existe_cambio:
                indice = banco.get_dic().index(cadena)
                valor = banco.get_cionario()[indice]
                if ahorro.get_banco() == banco:
                    valor *= 0.98
                if banco.is_asociado():
                    valor *= 0.97
                cuota_manejo = Banco.divisa_suscripcion(user)
                cotizacion = Movimientos(banco, ahorro, valor, cuota_manejo)
                imprimir.append(cotizacion)

        return imprimir

    @staticmethod
    def cotizar_taza_aux(user, existe_cambio, cadena, cuentas_posibles):
        from gestorAplicación.interno.movimientos import Movimientos
        imprimir = []
        for conta in cuentas_posibles:
            for banco in existe_cambio:
                indice = banco.get_dic().index(cadena)
                valor = banco.get_cionario()[indice]
                if conta.get_banco() == banco:
                    valor *= 0.98
                if banco.is_asociado():
                    valor *= 0.97
                cuota_manejo = Banco.divisa_suscripcion(user)
                cotizacion = Movimientos(banco, conta, valor, cuota_manejo)
                imprimir.append(cotizacion)

        return imprimir



    # Métodos de la funcionalidad Asesoramiento de inversiones
    def retornoPortafolio(riesgo, invertir, plazo, user):
        from gestorAplicación.interno.usuario import Usuario
        from gestorAplicación.interno.movimientos import Movimientos
        interes = random.random() + riesgo

        if len(user.cuentasAhorrosAsociadas) != 0 and user.cuentasAhorrosAsociadas[0].saldo > invertir:
            cobro = user.cuentasAhorrosAsociadas[0].saldo / 32
            movimiento = Movimientos(
                user.cuentasAhorrosAsociadas[0],
                Usuario.getUsuariosTotales()[Usuario.hallarUsuarioImpuestosPortafolio()].cuentasAhorrosAsociadas[0],
                cobro, Categoria.OTROS, datetime.now()
            )
            if movimiento.impuestosMovimiento(interes) and user.cuentasAhorrosAsociadas[0].saldo < invertir:
                Movimientos.movimientosTotales.remove(movimiento)
                return 1
            elif not movimiento.impuestosMovimiento(interes) and user.cuentasAhorrosAsociadas[0].saldo < invertir:
                Movimientos.movimientosTotales.remove(movimiento)
                return 2
            elif movimiento.impuestosMovimiento(interes) and user.cuentasAhorrosAsociadas[0].saldo > invertir:
                Movimientos.movimientosTotales.remove(movimiento)
                return 3
            elif not movimiento.impuestosMovimiento(interes) and user.cuentasAhorrosAsociadas[0].saldo > invertir:
                Movimientos.movimientosTotales.remove(movimiento)
                return 4

        elif len(user.cuentasAhorrosAsociadas) != 0 and user.cuentasAhorrosAsociadas[0].saldo < invertir:
            cobro = user.cuentasAhorrosAsociadas[0].saldo / 32
            movimiento = Movimientos(
                user.cuentasAhorrosAsociadas[0],
                Usuario.getUsuariosTotales()[Usuario.hallarUsuarioImpuestosPortafolio()].cuentasAhorrosAsociadas[0],
                cobro, Categoria.OTROS, datetime.now()
            )
            if movimiento.impuestosMovimiento(interes) and user.cuentasAhorrosAsociadas[0].saldo < invertir:
                Movimientos.movimientosTotales.remove(movimiento)
                return 5
            elif not movimiento.impuestosMovimiento(interes) and user.cuentasAhorrosAsociadas[0].saldo < invertir:
                Movimientos.movimientosTotales.remove(movimiento)
                return 6
            elif movimiento.impuestosMovimiento(interes) and user.cuentasAhorrosAsociadas[0].saldo > invertir:
                Movimientos.movimientosTotales.remove(movimiento)
                return 7
            elif not movimiento.impuestosMovimiento(interes) and user.cuentasAhorrosAsociadas[0].saldo > invertir:
                Movimientos.movimientosTotales.remove(movimiento)
                return 8

        elif len(user.cuentasCorrienteAsociadas) != 0 and user.cuentasCorrienteAsociadas[0].disponible > invertir:
            cobroCorriente = user.cuentasCorrienteAsociadas[0].disponible / 32
            movimientoCorriente = Movimientos(
                user.cuentasCorrienteAsociadas[0],
                Usuario.getUsuariosTotales()[Usuario.hallarUsuarioImpuestosPortafolio()].cuentasCorrienteAsociadas[0],
                cobroCorriente, Categoria.OTROS, datetime.now()
            )
            if movimientoCorriente.impuestosMovimiento(interes) and user.cuentasCorrienteAsociadas[0].disponible < invertir:
                Movimientos.movimientosTotales.remove(movimientoCorriente)
                return 1
            elif not movimientoCorriente.impuestosMovimiento(interes) and user.cuentasCorrienteAsociadas[0].disponible < invertir:
                Movimientos.movimientosTotales.remove(movimientoCorriente)
                return 2
            elif movimientoCorriente.impuestosMovimiento(interes) and user.cuentasCorrienteAsociadas[0].disponible > invertir:
                Movimientos.movimientosTotales.remove(movimientoCorriente)
                return 3
            elif not movimientoCorriente.impuestosMovimiento(interes) and user.cuentasCorrienteAsociadas[0].disponible > invertir:
                Movimientos.movimientosTotales.remove(movimientoCorriente)
                return 4

        else:
            cobroCorriente = user.cuentasCorrienteAsociadas[0].disponible / 32
            movimientoCorriente = Movimientos(
                user.cuentasCorrienteAsociadas[0],
                Usuario.getUsuariosTotales()[Usuario.hallarUsuarioImpuestosPortafolio()].cuentasCorrienteAsociadas[0],
                cobroCorriente, Categoria.OTROS, datetime.now()
            )
            if movimientoCorriente.impuestosMovimiento(interes) and user.cuentasCorrienteAsociadas[0].disponible < invertir:
                Movimientos.movimientosTotales.remove(movimientoCorriente)
                return 5
            elif not movimientoCorriente.impuestosMovimiento(interes) and user.cuentasCorrienteAsociadas[0].disponible < invertir:
                Movimientos.movimientosTotales.remove(movimientoCorriente)
                return 6
            elif movimientoCorriente.impuestosMovimiento(interes) and user.cuentasCorrienteAsociadas[0].disponible > invertir:
                Movimientos.movimientosTotales.remove(movimientoCorriente)
                return 7
            elif not movimientoCorriente.impuestosMovimiento(interes) and user.cuentasCorrienteAsociadas[0].disponible > invertir:
                Movimientos.movimientosTotales.remove(movimientoCorriente)
                return 8

        return 0
    
    def bancoPortafolio(user):
        banco = None
        if len(user.bancosAsociados) == 1:
            banco = user.bancosAsociados[0]
        else:
            for i in range(1, len(user.bancosAsociados)):
                if user.bancosAsociados[i - 1] != user.bancosAsociados[i]:
                    banco = user.bancosAsociados[i]
                else:
                    continue
        return banco


    def interesesPortafolio(banco, user):
        interes = 0.0

        for i in range(len(user.bancosAsociados)):
            if user.bancosAsociados[i] == banco:
                interes = round((interes + random.random() + i), 2)
        return interes

    def decisionCupo(self, suscripcion):
        cupo = 0
        if suscripcion == Suscripcion.DIAMANTE:
            cupo = self._cupo_base * (self._multiplicador * 3)
        elif suscripcion == Suscripcion.ORO:
            cupo = self._cupo_base * (self._multiplicador * 2)
        elif suscripcion == Suscripcion.PLATA:
            cupo = self._cupo_base * (self._multiplicador)
        else:
            cupo = self._cupo_base
        return cupo
    
    def getNombre(self):
        return self._nombre
