from .cuenta import Cuenta
from gestorAplicación.externo.banco import Banco
from datetime import date

class Ahorros(Cuenta):
    #Atributos de clase
    _cuentasAhorroTotales = []

    #Constructor
    def __init__(self, banco, clave, divisa, nombre, saldo):
        #Atributos de instancia
        Ahorros._cuentasAhorroTotales.append(self)
        super.__init__(banco, clave, divisa, nombre)
        self._saldo = saldo

#REVISAR SOBRECARGA

    def crearCuenta(self, banco, clave, divisa, nombre):
        return Ahorros(banco, clave, divisa, nombre)
    
    def __str__(self):
        return "Cuenta: " + self._nombre + "\nCuentas de Ahorros # " + self._id + "\nTitular: " + self.getTitular().getNombre() + "\nBanco: " + self._banco.getNombre() + "\nDivisa: " + self._divisa + "\nSaldo: " + self._saldo + " " + self._divisa
    
    # Método de la funcionalidad asesoramiento de inversiones
    def vaciarCuenta(self, gota):
        from .movimientos import Movimientos
        from .categoria import Categoria
        movimiento = Movimientos(self, gota, self.getSaldo(), Categoria.OTROS, date.now())
        self.getTitular().getMovimientosAsociados().append(movimiento)
        Movimientos.getMovimientosTotales().remove(movimiento)

    #Funcionalidad de Suscripciones de Usuarios
    def invertirSaldo(self):
        pass

    # Funcionalidad Prestamos
    @classmethod
    def comprobarPrestamo(cls,_cuentas):
        cuentasPrestamos = []
        bancos = []

        for cuenta in _cuentas:
            prestamos = cuenta.getBanco().getPrestamos()
            if(prestamos>0):
                cuentasPrestamos.append(cuenta)
            else:
                bancos.append(cuenta.getBanco())
        if len(cuentasPrestamos)!=0:
            return cuentasPrestamos
        else:
            return bancos

    @staticmethod
    def limpiarPropiedades(self, arreglo):
        pass

    #Métodos Get & Set
    def getSaldo(self):
        return self._saldo
    def setSaldo(self, saldo):
        self._saldo = saldo

    @classmethod
    def getCuentasAhorrosTotales(cls):
        return cls._cuentasAhorroTotales
    @classmethod
    def setCuentasAhorrosTotales(cls, cuentasAhorrosTotales):
        cls._cuentasAhorroTotales = cuentasAhorrosTotales