import pickle as pk
import os.path as os_query
from gestorAplicación.interno.usuario import Usuario
from gestorAplicación.interno.cuenta import Cuenta
from gestorAplicación.interno.metas import Metas
from gestorAplicación.interno.movimientos import Movimientos
from gestorAplicación.externo.estado import Estado
from gestorAplicación.externo.banco import Banco

class Deserializador():
    #Método para deserializar las listas con objetos de cualquier clase, que añade automáticamente los objetos a las listas de clase respectiva para cada clase
    @classmethod
    def deserializar(cls, clase) -> list:
        if(clase == "Usuarios"):
            if(os_query.isfile("src/baseDatos/temp/lista_usuarios.pkl")):
                objects_file = open("src/baseDatos/temp/lista_usuarios.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                if(len(objects_list) == 0):
                    #Do something(Mostrar una ventana emergente ó algo así)
                    pass
                else:
                    Usuario.setUsuariosTotales(objects_list)
            else:
                #Do something(Mostrar una ventana emergente ó algo así)
                pass
        if(clase == "Estados"):
            if(os_query.isfile("src/baseDatos/temp/lista_estados.pkl")):
                objects_file = open("src/baseDatos/temp/lista_estados.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                if(len(objects_list) == 0):
                    #Do something(Mostrar una ventana emergente ó algo así)
                    pass
                else:
                    Estado.setEstadosTotales(objects_list)
            else:
                #Do something(Mostrar una ventana emergente ó algo así)
                pass
        if(clase == "Bancos"):
            if(os_query.isfile("src/baseDatos/temp/lista_bancos.pkl")):
                objects_file = open("src/baseDatos/temp/lista_bancos.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                if(len(objects_list) == 0):
                    #Do something(Mostrar una ventana emergente ó algo así)
                    pass
                else:
                    Banco.setBancosTotales(objects_list)
            else:
                #Do something(Mostrar una ventana emergente ó algo así)
                pass
        if(clase == "Cuentas"):
            if(os_query.isfile("src/baseDatos/temp/lista_cuentas.pkl")):
                objects_file = open("src/baseDatos/temp/lista_cuentas.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                if(len(objects_list) == 0):
                    #Do something(Mostrar una ventana emergente ó algo así)
                    pass
                else:
                    Cuenta.setCuentasTotales(objects_list)
            else:
                #Do something(Mostrar una ventana emergente ó algo así)
                pass
        if(clase == "Movimientos"):
            if(os_query.isfile("src/baseDatos/temp/lista_movimientos.pkl")):
                objects_file = open("src/baseDatos/temp/lista_movimientos.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                if(len(objects_list) == 0):
                    #Do something(Mostrar una ventana emergente ó algo así)
                    pass
                else:
                    Movimientos.setMovimientosTotales(objects_list)
            else:
                #Do something(Mostrar una ventana emergente ó algo así)
                pass
        if(clase == "Metas"):
            if(os_query.isfile("src/baseDatos/temp/lista_metas.pkl")):
                objects_file = open("src/baseDatos/temp/lista_metas.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                if(len(objects_list) == 0):
                    #Do something(Mostrar una ventana emergente ó algo así)
                    pass
                else:
                    Metas.setMetasTotales(objects_list)
            else:
                #Do something(Mostrar una ventana emergente ó algo así)
                pass
