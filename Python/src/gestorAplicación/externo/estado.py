class Estado():
    _estadosTotales = []
    def __init__(self,nombre,tasa_impuestos,divisa):
        self._nombre = nombre
        self._tasa_impuestos = tasa_impuestos
        self._divisa = divisa
        self._id = len(Estado.estadosTotales)
        Estado.estadosTotales.append(self)
        self._interes_bancario_corriente
        self._tasa_usura

    def __str__(self):
        return f"{self.getNombre} Divisa: {self.getDivisa}"


# Set y Gest
    def getNombre(self):
        return self._nombre
    
    def getTasa_impuestos(self):
        return self._tasa_impuestos
    
    def getDivisa(self):
        return self._divisa
    
    def getId(self):
        return self._id
    
    def getEstadoTotales(self):
        return Estado._estadosTotales
    
    def getTasas_usura(self):
        return self._tasa_usura
    
    def getInteres_bancario_corriente(self):
        return self._Interes_bancario_corriente

    def setInteres_bancario_corriente(self,interes_bancario_corriente):
        self._interes_bancario_corriente= interes_bancario_corriente

    def setTasa_usura(self,tasa_usura):
        self._tasa_usura= tasa_usura

    def setTasa_impuestos(self,tasa_impuestos):
        self._tasa_impuestos= tasa_impuestos
        
    def setDivisa(self,divisa):
        self._divisa= divisa
    
    def setId(self,id):
        self._id= id
        
    def setNombre(self,nombre):
        self._nombre= nombre

