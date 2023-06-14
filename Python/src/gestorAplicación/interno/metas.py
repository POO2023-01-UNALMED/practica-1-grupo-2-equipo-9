from datetime import date

class Metas:
    _metasTotales = []
    _metaProxima = None
    _plazo = ""
    def __init__(self,**kwargs):
        self._nombre = None
        self.cantidad = None
        self._fecha = None
        self.dueno = None
        Metas._metasTotales.append(self)
        self._id = len(Metas._metasTotales)

        for key in kwargs:
            if key == "nombre":
                self._nombre = kwargs[key]
            if key == "cantidad":
                self.cantidad = kwargs[key]
            if key == "fecha":
                self._fecha = kwargs[key]
            if key == "dueno":
                self.dueno = kwargs[key]

    # Metodos asesoramiento Inversiones
    def revision_metas(user):
        proximaFecha = date.now()
        proximaMeta = None

        if user.getMetasAsociadas().size()==1:
            return user.getMetasAsociadas()[0]
        else:
            for meta in user.getMetasAsociadas():
                if meta.getFecha() != None:
                    proximaMeta = meta
                    break
                else:
                        continue

            for meta1 in user.getMetasAsociadas():
                for meta2 in user.getMetasAsociadas():
                    if meta2.getFecha() is None or meta1.getFecha() is None:
                        continue
                    else:
                        if meta2.getFecha() < meta1.getFecha():
                            if meta2.getFecha() < proximaFecha:
                                proximaFecha = meta2.getFecha()
                                proximaMeta = meta2
                            else:
                                continue
                        else:
                            continue

            return proximaMeta

    @staticmethod
    def cambio_fecha(meta, fecha):
        nuevaFecha = date.date.strptime(fecha, "%d/%m/%Y")
        meta.setFecha(nuevaFecha)
        return meta

    @staticmethod
    def determinar_plazo(meta):
        date1 = date.strptime("01/01/2024", "%d/%m/%Y")
        date2 = date.strptime("01/01/2026", "%d/%m/%Y")

        if meta.getFecha() < date1:
            Metas.plazo = "Corto"
        elif date1 < meta.getFecha() < date2:
            Metas.plazo = "Mediano"
        else:
            Metas.plazo = "Largo"

    @staticmethod
    def prioridad_metas(user, meta):
        user.getMetasAsociadas().pop(len(user.getMetasAsociadas) - 1)
        user.getMetasAsociadas().insert(0, meta)

    @staticmethod
    def limpiar_propiedades(arreglo):
        arreglo.remove("serialVersionUID")
        arreglo.remove("nombreD")
        arreglo.remove("metasTotales")
        arreglo.remove("DATE_FORMAT")
        arreglo.remove("metaProxima")
        arreglo.remove("plazo")
        
# Sets Y Gets
    
    def getNombre(self):
        return self._nombre
    def getCantidad(self):
        return self.cantidad
    def getFecha(self):
        return self._fecha
    def getDueno(self):
        return self.dueno
    def getMetasTotales():
        return Metas._metasTotales
    def getId(self):
        return self._id
    def getMetaProxima():
        return Metas._metaProxima
    def getPlazo():
        return Metas._plazo

    def setNombre(self,nombre):
        self._nombre = nombre 
    def setCantidad(self,cantidad):
        self.cantidad = cantidad
    def setFecha(self,fecha):
        self._fecha = fecha
    def setDueno(self,dueno):
        self.dueno = dueno
    def setMetasTotales(MetasTotales):
        Metas._metasTotales = MetasTotales
    def setId(self,id):
        self._id = id
    def setMetaProxima(metaProxima):
        Metas._metaProxima = metaProxima
    def setPlazo(plazo):
        Metas._plazo = plazo

        