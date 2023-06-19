from gestorAplicación.interno.metas import Metas

class Deuda(Metas):
    _deudasTotales = []
    def __init__(self,_cantidad,cuenta,_dueno,banco):
        super().__init__(cantidad = _cantidad,dueno =_dueno)
        self._cuenta = cuenta
        self._banco = banco
        Deuda._deudasTotales.append(self)        
        Metas._metasTotales.append(self)
    @classmethod
    def conseguirDeuda(cls,usuario):
        deudas = Deuda.getDeudasTotales();
        deudasUsuario = []
        for deuda in deudas:
            if deuda.getTitular == usuario:
                deudasUsuario.append(deuda)
        
        return deudasUsuario
    def getCuenta(self):
        return self._cuenta
    def getBanco(self):
        return self._banco
    def getDeudasTotales():
        return Deuda._deudasTotales
    
    def setCuenta(self,cuenta):
        self._cuenta = cuenta
    def setBanco(self,banco):
        self._banco = banco

    def __str__(self) -> str:
        return f"Dueno:{self.getDueno} \n Cuenta:{self.getCuenta} \n Cantida:{self.getCantidad} \n banco:{self.getBanco} \n"
    

        