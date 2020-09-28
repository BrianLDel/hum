Implementación del ejercicio de Tenis del PEI utilizando React para Frontend y Springboot para backend.

NOTA: este documento queda como un formalismo para cumplir con el estandar de Git. Pedir al administrdor del repositorio la guia completa de instalación, documento llamado "Setup de Ambiente Remoto - React-Java (RJ-PEI) 2020.doc"



### *Prerequisitos*

Tener instalado npm que viene con NodeJs. Luego en lo que respecta a React hay que seguir los pasos de la sección "Instalar dependencias".

###### NodeJs y NPM

Ir a https://nodejs.org/en/download/ y bajar el instalador correspondiente de la versión LTS.

NOTA: npm se instala con NodeJs

### *Instalar dependencias*

Esto lo realizará el comando npm install (que se detalla más abajo) la primera vez que lo ejecutemos, el cual busca de un repositorio de internet todo lo necesario.

1-Ir al raiz del proyecto tennis-app.

2-Ir a la carpeta **tennis-web**

3-Abrir una consola y ejecutar el siguiente comando:

*npm install*

Este comando nos permite descargar todas las dependencias y creará la carpeta node_modules que no está incluida en el repositorio.

### *Levantar frontend y compilar*

1-Ir al raiz del proyecto tennis-app.

2-Ir a la carpeta **tennis-web**

3-Abrir una consola y ejecutar el siguiente comando:

*npm start*

Levanta la aplicación en modo desarrollo. Abrir  [http://localhost:3000](http://localhost:3000/) para verla en un browser

La página se recarga automáticamente si se realizan cambios en el código. También es posible ver los errores por la consola. See docs\app_mianPage.JPG for a preview.

*npm run build*

Compila la aplicación para producción dentro de la carpeta build. HAce que React se configura en modo producción lo cual optimiza ciertos aspectos del código, además el compilado se mimifican entro otras cosas.

Para más detalles ver [*deployment*](https://facebook.github.io/create-react-app/docs/deployment)*.*

### *Testear frontend*

*npm test*

Ejecuta los test en modo interactivo. Para más detalles ver [*running tests*](https://facebook.github.io/create-react-app/docs/running-tests) *.*





## Referencia rápida de comandos.

In the project directory, you can run:

### npm install

This will download all dependencies node_modules folder will be created.

NOTE: use this if you have downloaded the repo directly or if you missed node_module folder.

### npm start

Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br>
You will also see any lint errors in the console.

See docs\app_mianPage.JPG for a preview.

### `npm test`

Launches the test runner in the interactive watch mode.<br>
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (Webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: https://facebook.github.io/create-react-app/docs/code-splitting

### Analyzing the Bundle Size

This section has moved here: https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size

### Making a Progressive Web App

This section has moved here: https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app

### Advanced Configuration

This section has moved here: https://facebook.github.io/create-react-app/docs/advanced-configuration

### Deployment

This section has moved here: https://facebook.github.io/create-react-app/docs/deployment

### `npm run build` fails to minify

This section has moved here: https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify
