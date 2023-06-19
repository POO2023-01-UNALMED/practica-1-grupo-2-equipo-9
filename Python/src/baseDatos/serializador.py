import pickle as pk
import os
from gestorAplicación.interno.usuario import Usuario
from gestorAplicación.interno.cuenta import Cuenta
from gestorAplicación.interno.metas import Metas
from gestorAplicación.interno.movimientos import Movimientos
from gestorAplicación.externo.estado import Estado
from gestorAplicación.externo.banco import Banco

class Serializador():

    # Obtener ruta relativa
    current_directory = os.path.dirname(os.path.abspath(__file__))
    route_db = os.path.join(current_directory + '\\temp')

    #Método para serializar las listas con objetos de cualquier clase
    @classmethod
    def serializar(cls, lista_objetos) -> None:
        if(isinstance(lista_objetos[0], Usuario)):
            objects_file = open(cls.route_db + "\lista_usuarios.pkl", "wb")
            pk.dump(lista_objetos, objects_file)
            objects_file.close()
        if(isinstance(lista_objetos[0], Banco)):
            objects_file = open(cls.route_db + "\lista_bancos.pkl", "wb")
            pk.dump(lista_objetos, objects_file)
            objects_file.close()
        if(isinstance(lista_objetos[0], Estado)):
            objects_file = open(cls.route_db + "\lista_estados.pkl", "wb")
            pk.dump(lista_objetos, objects_file)
            objects_file.close()
        if(isinstance(lista_objetos[0], Cuenta)):
            objects_file = open(cls.route_db + "\lista_cuentas.pkl", "wb")
            pk.dump(lista_objetos, objects_file)
            objects_file.close()
        if(isinstance(lista_objetos[0], Movimientos)):
            objects_file = open(cls.route_db + "\lista_movimientos.pkl", "wb")
            pk.dump(lista_objetos, objects_file)
            objects_file.close()
        if(isinstance(lista_objetos[0], Metas)):
            objects_file = open(cls.route_db + "\lista_metas.pkl", "wb")
            pk.dump(lista_objetos, objects_file)
            objects_file.close()