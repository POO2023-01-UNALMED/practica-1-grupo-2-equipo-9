from abc import ABC, abstractmethod
from datetime import date

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