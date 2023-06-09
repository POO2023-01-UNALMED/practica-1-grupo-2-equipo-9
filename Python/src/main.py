import tkinter as tk
from tkinter import font, messagebox, ttk, Button
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

#FAVOR SER ORDENADOS CON EL CÓDIGO Y COMENTAR TODO BIEN

welcomewindow = None

def BienvenidaApp():
    #Configuración básica de parámetros de la ventana de inicio
    welcomewindow = tk.Tk()
    welcomewindow.geometry("1000x800")
    welcomewindow.title("Mis Finanzas")
    #welcomewindow.resizable(0,0)
    current_directory = os.path.dirname(os.path.abspath(__file__))
    route_logo = os.path.join(current_directory + "\static", "logo.png")
    logo = tk.PhotoImage(file = route_logo)
    welcomewindow.iconphoto(True, logo)
    
    #Métodos de funcionamiento de la ventana de inicio
    def Exit_initial_window():
        welcomewindow.destroy()

    def Show_description():
        style_description = ttk.Style()
        style_description.configure("Style_description.TLabel", font=("Alegreya Sans", 12, "bold"), foreground="blue")
        #Dar formato de estilo
        messagebox.showinfo("Descripción del sistema","Mis Finanzas es una plataforma de gestión financiera digital que brinda a los usuarios la capacidad de administrar y controlar sus recursos monetarios de manera eficiente. El propósito fundamental de Mis Finanzas es mejorar la relación que las personas tienen con su dinero, proporcionando diversas funcionalidades diseñadas para ofrecer a los usuarios una amplia gama de opciones sobre cómo utilizar sus fondos y obtener el máximo beneficio de ellos. Esta plataforma permite a los usuarios realizar un seguimiento detallado de sus ingresos, gastos y ahorros, brindando una visión integral de su situación financiera. Además, ofrece herramientas para establecer y monitorear metas financieras, como ahorros para un objetivo específico o la realización de préstamos.")


    #Configuración básica de los parámetros del mainframe en la ventana de inicio
    mainframe = tk.Frame(welcomewindow, bg="#DFDEDE")
    mainframe.pack(fill="both", expand=True)

    #Configuración de los sub-frames anidados al mainframe de la ventana de inicio
    upperframe = tk.Frame(mainframe, bg="black", borderwidth=1, relief="solid")
    upperframe.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)

    home_menu = tk.Menu(upperframe)
    menu_options= tk.Menu(home_menu, tearoff=0)
    menu_options.add_command(label="Salir de la aplicación", command=Exit_initial_window)
    menu_options.add_command(label="Descripción del sistema", command=Show_description)
    menu_options.add_separator()
    menu_options.add_command(label="Guardar objetos")
    menu_options.add_command(label="Cargar objetos")
    home_menu.add_cascade(label="Inicio", menu=menu_options)
    welcomewindow.config(menu=home_menu)

    leftframe = tk.Frame(mainframe, bg="white", borderwidth=1, relief="solid")
    leftframe.place(anchor="w", relheight=0.85, relwidth=0.46, rely=0.55, relx=0.03)

    rightframe = tk.Frame(mainframe, bg="white", borderwidth=1, relief="solid")
    rightframe.place(anchor="e", relheight=0.85, relwidth=0.46, relx=0.97, rely=0.55)

    #Configuración de los sub-frames anidados a cada uno de los sub-frames anidados a mainframe de la ventana de inicio
    upperleftframe = tk.Frame(leftframe, bg="white", borderwidth=1, relief="groove")
    upperleftframe.place(anchor="n", relheight=0.35, relwidth=0.993, rely=0.002, relx=0.5)

    bottomleftframe = tk.Frame(leftframe, bg="white", borderwidth=1, relief="groove")
    bottomleftframe.place(anchor="s", relheight=0.642, relwidth=0.993, rely=0.998, relx=0.5)
    
    upperrightframe = tk.Frame(rightframe, bg="white", borderwidth=1, relief="groove")
    upperrightframe.place(anchor="n", relheight=0.35, relwidth=0.993, rely=0.002, relx=0.5)

    bottomrightframe = tk.Frame(rightframe, bg="white", borderwidth=1, relief="groove")
    bottomrightframe.place(anchor="s", relheight=0.642, relwidth=0.993, rely=0.998, relx=0.5)

    #Configuración de los nodos que se ubicarán en los sub-frames anidados a cada uno de los sub-frames anidados a mainframe de la ventana de inicio

    #-------Texto de título(upperframe)---------------------
    upper_label_text_variable = "Mis Finanzas"
    upper_text_font_style = font.Font(weight="bold", size=12, family="Alegreya Sans")
    title_label = tk.Label(upperframe, text=upper_label_text_variable, fg="white", bg="gray", wraplength=400, font=upper_text_font_style, width=50)
    title_label.place(anchor="w", relheight=0.97, relwidth=0.8945, rely=0.5, relx=0.001)
    #--------------------------------------------------
    #-------Imágen del título(upperframe)---------------------
    route_image = os.path.join(current_directory + "\static", "unal.png")    
    upper_image = tk.PhotoImage(file=route_image)
    upper_image = upper_image.subsample(4)
    upper_image_label = tk.Label(upperframe, image=upper_image)
    upper_image_label.place(anchor="e", relheight=0.97, relwidth=0.101, rely=0.5, relx=0.999)
    #--------------------------------------------------
    #-------Texto de bienvenida(P3 - upperleftframe)---------------------
    welcome_label_text_variable = "Bienvenidos al sistema de gestión financiera Mis Finanzas programado por: \n->Juan Pablo Mejía Gómez.\n->Leonard David Vivas Dallos.\n->José Daniel Moreno Ceballos.\n->Tomás Escobar Rivera.\n->Jorge Humberto García Botero."
    welcome_label = tk.Label(upperleftframe, text = welcome_label_text_variable, fg="black", bg="white", wraplength=400, font=("Alegreya Sans", 12))
    welcome_label.pack(expand=True, fill="both", padx=2, pady=2)
    #--------------------------------------------------
    #-------Hoja de vida de los desarroladores(P5 - upperrightframe)---------------------
    def change_button_text():
        if button["text"] == "Hoja de vida 1":
            button["text"] = "Hoja de vida 2"
        elif button["text"] == "Hoja de vida 2":
            button["text"] = "Hoja de vida 3"
        elif button["text"] == "Hoja de vida 3":
            button["text"] = "Hoja de vida 4"
        elif button["text"] == "Hoja de vida 4":
            button["text"] = "Hoja de vida 5"
        elif button["text"] == "Hoja de vida 5":
            button["text"] = "Hoja de vida 1"
    # Crear el botón y asociar la función change_button_text con él
    button = Button(upperrightframe, text="Hoja de vida 1", command=change_button_text)
    button.pack(expand=True, fill="both")
    #--------------------------------------------------
    welcomewindow.mainloop()

if __name__ == "__main__":
    #Poner código para ejecutar la interfaz
    BienvenidaApp()