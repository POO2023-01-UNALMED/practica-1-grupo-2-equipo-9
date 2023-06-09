import random
from datetime import datetime
from gestorAplicación.interno.movimientos import Movimientos
from gestorAplicación.interno.usuario import Usuario
from gestorAplicación.interno.categoria import Categoria

class Banco():

    # Métodos de la funcionalidad Asesoramiento de inversiones
    def retornoPortafolio(riesgo, invertir, plazo, user):
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
