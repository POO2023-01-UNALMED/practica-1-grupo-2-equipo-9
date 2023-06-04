import tkinter as tk
import os
from baseDatos.deserializador import Deserializador
from baseDatos.serializador import Serializador
from gestorAplicación.interno.usuario import Usuario
from gestorAplicación.interno.cuenta import Cuenta
from gestorAplicación.interno.metas import Metas
from gestorAplicación.interno.suscripcion import Suscripcion
from gestorAplicación.interno.movimientos import Movimientos
from gestorAplicación.externo.estado import Estado
from gestorAplicación.externo.banco import Banco

def BienvenidaApp():
    #Configuración básica de parámetros de la ventana de inicio
    welcomewindow = tk.Tk()
    welcomewindow.geometry("800x600")
    welcomewindow.title("Mis Finanzas")
    #welcomewindow.resizable(0,0)
    current_directory = os.path.dirname(os.path.abspath(__file__))
    route_logo = os.path.join(current_directory, "logo.png")
    logo = tk.PhotoImage(file = route_logo)
    welcomewindow.iconphoto(True, logo)

    #Configuración básica de los parámetros del mainframe en la ventana de inicio
    mainframe = tk.Frame(welcomewindow, bg="#DFDEDE")
    mainframe.pack(fill="both", expand=True)

    #Configuración de los sub-frames anidados al mainframe de la ventana de inicio
    upperframe = tk.Frame(mainframe, bg="white", borderwidth=1, relief="solid")
    upperframe.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)

    home_menu = tk.Menu(upperframe)
    menu_options= tk.Menu(home_menu, tearoff=0)
    menu_options.add_command(label = "Salir de la aplicación")
    menu_options.add_command(label = "Descripción del sistema")
    menu_options.add_separator()
    menu_options.add_command(label = "Guardar")
    menu_options.add_command(label = "Cargar")
    home_menu.add_cascade(label = "Inicio", menu = menu_options)
    welcomewindow.config(menu = home_menu)

    leftframe = tk.Frame(mainframe, bg="white", borderwidth=1, relief="solid")
    leftframe.place(anchor="w", relheight=0.85, relwidth=0.46, rely=0.55, relx=0.03)

    rightframe = tk.Frame(mainframe, bg="white", borderwidth=1, relief="solid")
    rightframe.place(anchor="e", relheight=0.85, relwidth=0.46, relx=0.97, rely=0.55)

    #Configuración de los sub-frames anidados a cada uno de los sub-frames anidados a mainframe de la ventana de inicio
    upperleftframe = tk.Frame(leftframe, bg="white", borderwidth=1, relief="groove")
    upperleftframe.place(anchor="n", relheight=0.42, relwidth=0.993, rely=0.002, relx=0.5)

    bottomleftframe = tk.Frame(leftframe, bg="white", borderwidth=1, relief="groove")
    bottomleftframe.place(anchor="s", relheight=0.573, relwidth=0.993, rely=0.998, relx=0.5)
    
    upperrightframe = tk.Frame(rightframe, bg="white", borderwidth=1, relief="groove")
    upperrightframe.place(anchor="n", relheight=0.42, relwidth=0.993, rely=0.002, relx=0.5)

    bottomrightframe = tk.Frame(rightframe, bg="white", borderwidth=1, relief="groove")
    bottomrightframe.place(anchor="s", relheight=0.573, relwidth=0.993, rely=0.998, relx=0.5)
    
    welcomewindow.mainloop()

if __name__ == "__main__":
    #Poner código para ejecutar la interfaz
    BienvenidaApp()