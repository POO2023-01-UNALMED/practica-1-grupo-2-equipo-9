from abc import ABC, abstractmethod
from datetime import date, datetime
from Python.src.gestorAplicación.interno.movimientos import Movimientos

class Cuenta(ABC):
    #Atributos de clase
    _cuentasTotales = []

    #Constructor
    def __init__(self, banco, clave, divisa, nombre):
        #Atributos de instancia
        Cuenta._cuentasTotales.append(self)
        self._banco = banco
        self._clave = clave
        self._divisa = divisa
        self._nombre = nombre
        self._id = len(Cuenta._cuentasTotales)
#REVISAR SOBRECARGA

    @abstractmethod
    def crearCuenta(self, banco, clave, divisa, nombre):
        pass

    def __str__(self):
        return "Cuenta: " + self._nombre + "\n# " + self._id + "\nTitular: " + self.getTitular().getNombre() + "\nBanco: " + self._banco.getNombre() + "\nDivisa: " + self._divisa

    # Métodos para la funcionalidad asesoramiento de inversiones
    def gotaGota(cantidadPrestamo, user, gota):
        from .movimientos import Movimientos
        from .categoria import Categoria
        mayor = 0
        contador = 0
        if len(user.cuentasAhorrosAsociadas) != 0:
            for i in range(len(user.cuentasAhorrosAsociadas)):
                if user.cuentasAhorrosAsociadas[i].saldo > mayor:
                    mayor = user.cuentasAhorrosAsociadas[i].saldo
                    contador = i
                movimiento = Movimientos(gota, user.cuentasAhorrosAsociadas[contador], cantidadPrestamo, Categoria.OTROS, date.now)
                Movimientos.getMovimientosTotales().remove(movimiento)
            return user.cuentasAhorrosAsociadas[contador]
        else:
            for i in range(len(user.cuentasCorrienteAsociadas)):
                if user.cuentasCorrienteAsociadas[i].cupo > mayor:
                    mayor = user.cuentasCorrienteAsociadas[i].cupo
                    contador = i
                movimiento = Movimientos(gota, user.cuentasCorrienteAsociadas[contador], cantidadPrestamo, Categoria.OTROS, date.now)
                Movimientos.getMovimientosTotales().remove(movimiento)
            return user.cuentasCorrienteAsociadas[contador]
        
    def vaciarCuenta(gota):
        pass

    #Métodos para funcionalidad de cambio de divisa
    @staticmethod
    def hacer_cambio(escogencia, monto, destino, user, exacto=False):
        origen = escogencia.get_origen()
        if exacto:
            pagar = monto / (1 - escogencia.get_banco().get_estado_asociado().get_tasa_impuestos())
            pagar /= (1 - escogencia.get_couta_manejo())
            pagar /= escogencia.get_cantidad()
            m = Movimientos(escogencia.get_banco(), origen, destino, escogencia.get_divisa(), escogencia.get_divisa_aux(), escogencia.get_couta_manejo(), pagar, datetime.datetime.now())
            origen.set_saldo(origen.get_saldo() - pagar)
            destino.set_saldo(destino.get_saldo() + monto)
        else:
            cambiado = monto * (1 - escogencia.get_banco().get_estado_asociado().get_tasa_impuestos())
            cambiado *= (1 - escogencia.get_couta_manejo())
            cambiado *= escogencia.get_cantidad()
            m = Movimientos(escogencia.get_banco(), origen, destino, escogencia.get_divisa(), escogencia.get_divisa_aux(), escogencia.get_couta_manejo(), monto, datetime.datetime.now())
            origen.set_saldo(origen.get_saldo() - monto)
            destino.set_saldo(destino.get_saldo() + cambiado)

        user.asociar_movimiento(m)
        for i in range(len(user.get_bancos_asociados())):
            user.get_bancos_asociados()[i].set_asociado(False)

    @staticmethod
    def comprobar_saldo(cuenta, monto=None):
        ahorro = cuenta
        if monto is None:
            return ahorro.get_saldo() >= monto
        else:
            pagar = monto / (1 - cuenta.get_banco().get_estado_asociado().get_tasa_impuestos())
            pagar /= (1 - cuenta.get_couta_manejo())
            pagar /= cuenta.get_cantidad()
            return ahorro.get_saldo() >= pagar

    @staticmethod
    def obtener_cuentas_divisa(usuario, divisa):
        cuentas_b = []
        for ahorro in usuario.get_cuentas_ahorros_asociadas():
            if ahorro.get_divisa() == divisa:
                cuentas_b.append(ahorro)
        return cuentas_b


    #Realizar el método del compare to
    def compareTo(self, cuenta):
        if self.getId() > cuenta.getId():
            return 1
        elif self.getId() < cuenta.getId():
            return -1
        else:
            return 0

    #¿Se maneja ligadura dinámica?
    def invertirSaldo(self):
        pass

    def retornoCuotaMensual(self, deudaActual):
        banco = self.getBanco()
        cuotaMensual = []
        if (banco.getComision() + banco.getEstadoAsociado().getInteres_bancario_corriente()) < 1:
            #Cuota del estado y del banco
            cuota1 = deudaActual*banco.getComision() + banco.getEstadoAsociado().getIntereses_bancario_corriente()*deudaActual
            cuota2 = (deudaActual - cuota1) / 2
            cuotaMensual[0] = deudaActual*banco.getComision() + banco.getEstadoAsociado().getInteres_bancario_corriente()*deudaActual
            cuotaMensual[1] = cuota2
            cuotaMensual[2] = cuota2
        else:
            cuotaMensual[0] = deudaActual / 3
            cuotaMensual[1] = deudaActual / 3
            cuotaMensual[2] = deudaActual / 3
        return cuotaMensual

    #Realizar el método equals
    def equals(self, cuenta):
        if self.getId() == cuenta.getId():
            return True
        else:
            return False

    @staticmethod
    def limpiarPropiedades(cls, arreglo):
        arreglo.remove("cuentasTotales")
        #Verificar que otras variables se crean
    #Verificar su uso

    @staticmethod
    def dineroATenerDisponible(cls, cuenta, divisas):
        pass

    def __str__(self):
        return "Cuenta: " + self._nombre + "\n# " + self._id + "\nTitular: " + self.getTitular.getNombre() + "\nBanco: " + self._banco.getNombre() + "\nDivisa: " + self._divisa

    #Métodos Get & Set
    @classmethod
    def getCuentasTotales(cls):
        return cls._cuentasTotales
    @classmethod
    def setCuentasTotales(cls, cuentasTotales):
        cls._cuentasTotales = cuentasTotales

    def getTitular(self):
        return self._titular
    def setTitular(self, titular):
        self._titular = titular

    def getDivisa(self):
        return self._divisa
    def setDivisa(self, divisa):
        self._divisa = divisa

    def getNombre(self):
        return self._nombre
    def setNombre(self, nombre):
        self._nombre = nombre

    def getId(self):
        return self._id
    def setId(self, id):
        self._id = id

    def getBanco(self):
        return self._banco
    def setBanco(self, banco):
        self._banco = banco

    def getClave(self):
        return self._clave
    def setClave(self, clave):
        self._clave = clave