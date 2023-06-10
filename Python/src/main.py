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
image_index = 0 # Variable para realizar un seguimiento del índice del pack de imagenes de los desarrolladores

def App():
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
        messagebox.showinfo("Mis Finanzas","Mis Finanzas es una plataforma de gestión financiera digital que brinda a los usuarios la capacidad de administrar y controlar sus recursos monetarios de manera eficiente. El propósito fundamental de Mis Finanzas es mejorar la relación que las personas tienen con su dinero, proporcionando diversas funcionalidades diseñadas para ofrecer a los usuarios una amplia gama de opciones sobre cómo utilizar sus fondos y obtener el máximo beneficio de ellos. Esta plataforma permite a los usuarios realizar un seguimiento detallado de sus ingresos, gastos y ahorros, brindando una visión integral de su situación financiera. Además, ofrece herramientas para establecer y monitorear metas financieras, como ahorros para un objetivo específico o la realización de préstamos.")


    #Configuración básica de los parámetros del mainframe en la ventana de inicio
    mainframe = tk.Frame(welcomewindow, bg="#DFDEDE")
    mainframe.pack(fill="both", expand=True)

    #Configuración de los sub-frames anidados al mainframe de la ventana de inicio
    upperframe = tk.Frame(mainframe, bg="black", borderwidth=1, relief="solid")
    upperframe.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)

    #Configuración de menú de inicio
    home_menu = tk.Menu(upperframe, cursor="cross")
    menu_options= tk.Menu(home_menu, tearoff=0)
    menu_options.add_command(label="Salir de la aplicación", command=Exit_initial_window, activebackground="gray", activeforeground="white")
    menu_options.add_command(label="Descripción del sistema", command=Show_description, activebackground="gray", activeforeground="white")
    menu_options.add_separator()
    menu_options.add_command(label="Guardar objetos", activebackground="gray", activeforeground="white")
    menu_options.add_command(label="Cargar objetos", activebackground="gray", activeforeground="white")
    home_menu.add_cascade(label="Inicio", menu=menu_options, activebackground="gray", activeforeground="white")
    welcomewindow.config(menu = home_menu)

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
    welcome_label = tk.Text(upperleftframe, cursor="cross", fg="black", bg="white", font=("Alegreya Sans", 12), wrap="word", spacing1=8)
    welcome_label.insert(tk.INSERT, welcome_label_text_variable)
    welcome_label.tag_configure("justifying", justify="center")
    welcome_label.tag_add("justifying", "1.0", tk.END)
    welcome_label.config(state="disabled")
    welcome_label.pack(expand=True, fill="both", side="bottom")
    #--------------------------------------------------
    #-------Hoja de vida de los desarrolladores(P5 - upperrightframe)---------------------
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
        update_image()
    # Crear el botón y asociar la función change_button_text con él
    button = Button(upperrightframe, text="Hoja de vida 1", bg="white", command=change_button_text, font=("Alegreya Sans", 12), activebackground="gray", activeforeground="white", border=1, relief="groove", cursor="cross")
    button.pack(expand=True, fill="both")
    #--------------------------------------------------
    #-------Fotos de los desarrolladores(P6 - bottomrightframe)---------------------
    def update_image():
        global image_index
        image_paths = [
            # Pack de imagenes 1
            route_image,
            route_logo,
            route_logo,
            route_logo,
            # Pack de imagenes 2
            route_logo,
            route_image,
            route_logo,
            route_logo,
            # Pack de imagenes 3
            route_logo,
            route_logo,
            route_image,
            route_logo,
            # Pack de imagenes 4
            route_logo,
            route_logo,
            route_logo,
            route_image,
            # Pack de imagenes 5
            route_logo,
            route_logo,
            route_logo,
            route_logo,
        ]

        # Borrar cualquier sub-frame existente
        for subframe in bottomrightframe.winfo_children():
            subframe.destroy()

        # Divide el marco inferior derecho en dos filas y dos columnas.
        for i in range(0, 2):
            bottomrightframe.rowconfigure(i, weight=1)
            bottomrightframe.columnconfigure(i, weight=1)

        # Crear sub-frames para las imágenes superiores
        for i in range(0, 2):
            # Calcular el índice de la imagen actual
            current_image_index = (image_index + i) % len(image_paths)

            # Cargar la imagen actual
            image_path = image_paths[current_image_index]
            image = tk.PhotoImage(file=image_path)

            # Crear un sub-frame para mostrar la imagen
            subframe = tk.Frame(bottomrightframe, bg="white", borderwidth=1, relief="groove")
            subframe.grid(row=0, column=i, sticky="nsew")

            # Crear un label para mostrar la imagen
            image_label = tk.Label(subframe, image=image)
            image_label.image = image  # Keep a reference to the image to prevent it from being garbage collected
            image_label.pack(expand=True, fill="both")

        # Crear sub-frame para las imágenes inferiores
        for i in range(0, 2):
            # Calcular el índice de la imagen actual
            current_image_index = (image_index + i + 2) % len(image_paths)

            # Cargar la imagen actual
            image_path = image_paths[current_image_index]
            image = tk.PhotoImage(file=image_path)

            # Crear un sub-frame para mostrar la imagen
            subframe = tk.Frame(bottomrightframe, bg="white", borderwidth=1, relief="groove")
            subframe.grid(row=1, column=i, sticky="nsew")

            # Crear un label para mostrar la imagen
            image_label = tk.Label(subframe, image=image)
            image_label.image = image  # Keep a reference to the image to prevent it from being garbage collected
            image_label.pack(expand=True, fill="both")

        # Incrementar el índice de la imagen
        image_index = (image_index + 4) % len(image_paths)

    image_label = tk.Label(bottomrightframe)
    image_label.pack(expand=True, fill="both")
    update_image()
    #--------------------------------------------------
    #-------Imágenes asociadas al sistema(P4 - bottomleftframe)---------------------
    # Crear un label para mostrar la imagen.
    image_label = tk.Label(bottomleftframe)
    image_label.pack()

    # Lista de las imágenes asociadas al sistema
    image_paths = [
        route_logo,
        route_image,
        route_logo,
        route_image,
        route_logo
    ]
    
    # Cargar las imágenes
    images = [tk.PhotoImage(file=image_path) for image_path in image_paths]

    # Mostrar la imagen inicial
    current_image_index = 0
    current_image = images[current_image_index]
    image_label.config(image=current_image)

    # Función para cambiar la imagen al hacer clic
    def cambiar_imagen_sistema(event):
        nonlocal current_image_index
        current_image_index = (current_image_index + 1) % len(images)
        current_image = images[current_image_index]
        image_label.config(image=current_image)

    # Vincular el evento de clic al label de la imagen
    image_label.bind("<Button-1>", cambiar_imagen_sistema)
    #--------------------------------------------------
    welcomewindow.mainloop()

if __name__ == "__main__":
    #Poner código para ejecutar la interfaz
    App()