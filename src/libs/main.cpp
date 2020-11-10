#include <iostream>
#include <random>
#include<fstream>
#include<string>
#include <sstream>
#include <algorithm> // Uso de .sort()
#include "testFunctions.h"

namespace funct = TestFunctions; //Usamos un espacio de nombres para no tener que crear objetos cada vez que se llame a una función
using namespace std;
using namespace funct;

class Particle {
private:
    vector<float> position;
    vector<float> velocity;
    vector<float> bestPosition;
    float fitness, bestFitness; //El valor que arroja tu funcion de prueba
public:
    int id;

    Particle() {};

    ~Particle() {};

    Particle(int _id) { id = _id; }

    string toString() {
        stringstream ss;
        ss << id << "\t"
           << fitness << "\t"
           << bestFitness << "\t";
        ss << "(";
        for (int i = 0; i < position.size(); i++)
            ss << position.at(i) << ",";
        ss << ")";
        ss << "\t(";

        for (int i = 0; i < bestPosition.size(); i++)
            ss << bestPosition.at(i) << ",";
        ss << ")";
        ss << "\t(";

        for (int i = 0; i < velocity.size(); i++)
            ss << velocity.at(i) << ",";
        ss << ")";

        return ss.str();

    }

    //Getters
    vector<float> getPosition() { return position; }

    vector<float> getVelocity() { return velocity; }

    vector<float> getBestPosition() { return bestPosition; }

    float getFitness() { return fitness; }

    float getBestFitness() const {
        return bestFitness;
    }


    //setter

    void setPosition(vector<float> m_position) {
        //1P= donde inicia la copia. 2P= donde termina. 3P = back_inserter
        position.clear();
        copy(m_position.begin(), m_position.end(), back_inserter(position));
    }

    void setBestPosition(vector<float> _bestposition) {
        bestPosition.clear();
        copy(_bestposition.begin(), _bestposition.end(), back_inserter(bestPosition));
    }

    void setVelocity(vector<float> _velocity) {
        velocity.clear();
        copy(_velocity.begin(), _velocity.end(), back_inserter(velocity));

    }

    void setFitness(float fitness) {
        Particle::fitness = fitness;
    }

    void setBestFitness(float bestFitness) {
        Particle::bestFitness = bestFitness;
    }

    void setid(int _id) { id = _id; }

    bool operator<(const Particle &p) const { return (fitness < p.fitness); }
};

void printPopulation(vector<Particle> population, int benchmarkFunct, string flag, Particle g) {

    const string header = "id\tfitness\tbest_fitness\tposition\tbestPosition\tvelocity\n";
    string fileName;

    switch (benchmarkFunct) {
        case 1:
            fileName = "sphere_bitacora.txt";
            break;
        case 2:
            fileName = "goldstein_bitacora.txt";
            break;
        case 3:
            fileName = "mcCormick_bitacora.txt";
            break;
        case 4:
            fileName = "eggHolder_bitatora.txt";
            break;
        case 5:
            fileName = "beale_bitacora.txt";
            break;
        default:
            fileName = "default_bitacora.txt";
            break;
    }

    //Guardando en txt
    ofstream ofs, ofs2;
    ofs.open(fileName, iostream::app);

    //Aqui imprimimos en el archivo especifico, ej: bitacora_sphere, beale, eggholder etc.
    string linea;
    ifstream archivo;
    archivo.open(fileName);

    int n_lineas = 0;
    for (n_lineas; getline(archivo, linea); n_lineas++);
    if (n_lineas < 1) ofs << header; //Solo si el archivo tiene menos de 1 linea

    archivo.close();
    n_lineas = 0;
    ofs << "* " << flag << "* \n";

    ofs << "| PRIMEROS Y ULTIMOS 3|\n" + header;
    for (int i = 0; i < 3; i++) {
        ofs << population.at(i).toString() + "\n";
    }

    ofs << "....lineas omitidas  " << endl;

    for (int i = population.size() - 3; i < population.size(); i++) {
        ofs << population.at(i).toString() + "\n";
    }
    ofs << "----------------------------------\n";

    //Aqui imprimimos en el archivo general de bitacora, el cual es bitacora.txt

    ofs2.open("Bitacora.txt", iostream::app);
    archivo.open("Bitacora.txt");

    //Contamos lineas otra vez para escribir o no el header
    for (n_lineas; getline(archivo, linea); n_lineas++);
    if (n_lineas < 1) ofs2 << header; //Solo si el archivo tiene menos de 1 linea
    archivo.close();

    ofs2 << "* " << flag << "* \n";
    for (Particle p : population) ofs2 << p.toString() + "\n";
    ofs2 << endl << endl << endl;
    ofs2 << "La mejor particula de " + flag + " -> " + g.toString() << endl;

}

//Valor de Omega (peso inercia) iteracion 1 = 0.9 dividir entre el numero de iteraciones y multiplicarlo en cada una de ellas
vector<float> _LB(float x)
{
    vector<float>LB;
    LB.clear();
    LB.push_back(x);
    LB.push_back(x);
    return LB;
}
vector<float> _UB(float x)
{
    vector<float>UB;
    UB.clear();
    UB.push_back(x);
    UB.push_back(x);
    return UB;
}

void actualizarParticulas(int iteraciones, float (*fp)(vector<float>), int fKey, vector<float> LB, vector<float> UB) {

    vector<Particle> population;
    Particle temp;
    Particle g;
    float x, y, f, omega=0.9, decremento;
    vector<float> posTemp;
    int D = 2;


    for (int i = 1; i <= 100; i++) { //Creando las 100 particulas
        temp = Particle(i);
        x = randGen(LB.at(0), UB.at(0));
        y = randGen(LB.at(1), UB.at(1));

        posTemp.push_back(x);
        posTemp.push_back(y);
        f = fp(posTemp);

        temp.setFitness(f);
        temp.setBestFitness(f);
        temp.setPosition(posTemp);
        temp.setBestPosition(posTemp);
        temp.setVelocity(posTemp);
        population.push_back(temp);

        posTemp.clear();
    }
    sort(population.begin(), population.end());
    g.setFitness(numeric_limits<float>::max());

    //Calculo del decremento de omega
    decremento= (0.9-0.4)/(iteraciones-1);
    // Recibe la direccion en memoria del vector de particulas por que la poblacion de particulas sufre cambios y despues queremos acceder a los cambios que se hacen
    for (int i = 1; i <= iteraciones; i++) {

        for (int i = 0; i < population.size(); i++) {

            if (population.at(i).getFitness() < population.at(i).getBestFitness()) {
                population.at(i).setBestFitness(population.at(i).getFitness());
                population.at(i).setBestPosition(population.at(i).getPosition());
            }
        }

        sort(population.begin(), population.end()); //Ordenar el vector de particulas en base al Fitness

        if (population.at(0).getFitness() < g.getFitness()) {
            g.setid(0);
            g.setFitness(population.at(0).getFitness());
            g.setBestFitness(population.at(0).getBestFitness());
            g.setPosition(population.at(0).getPosition());
            g.setBestPosition(population.at(0).getBestPosition());
            g.setVelocity(population.at(0).getVelocity());
        }
        else
            population.at(0)=g;

        cout<<"La mejor particula de la iteracion "<< i <<" = " <<endl<<"id\tfitness\tbest_fitness\tposition\tbestPosition\tvelocity\n"<<g.toString()<<endl;
        //Decrecimiento de omega;
	    if (i==1)
            omega= omega;
        else
            omega= omega-decremento;

        vector<float> term1, term2,resvelocidad, sumaterm, resposition, pactual, vactual;
        for (int i = 0; i < population.size(); i++) {
            term1 = multVector(U(0, 2, 2),
                               restaVector(population.at(i).getBestPosition(), population.at(i).getPosition()));
            term2 = multVector(U(0, 2, 2), restaVector(g.getPosition(),
                                                       population.at(
                                                               i).getPosition())); // g est� declarado arriba y es la posicion de la mejor particula en el vecindario
            sumaterm=sumaVector(term1, term2);
            resvelocidad= multi_escalar(omega,sumaVector(population.at(i).getVelocity(),sumaterm));
            resposition = sumaVector(population.at(i).getPosition(), resvelocidad);

            for (int m = 0; m < D; m++) {
                if (resvelocidad.at(m) < LB.at(m)) resvelocidad.at(m) = LB.at(m);
                if (resvelocidad.at(m) > UB.at(m)) resvelocidad.at(m) = UB.at(m);

                if (resposition.at(m) < LB.at(m)) resposition.at(m) = LB.at(m);
                if (resposition.at(m) > UB.at(m)) resposition.at(m) = UB.at(m);
            }

            population.at(i).setVelocity(resvelocidad);
            population.at(i).setPosition(resposition);
            population.at(i).setFitness(fp(population.at(i).getPosition()));

        }

        printPopulation(population, fKey, "Iteración: " + to_string(i), g);

    }
}

int main() {
    int opcion;

    cout << "Optimización por enjambre de particulas (PSO)" << endl << "Elige la función de optimización:" << endl
         << "1. Sphere" << endl << "2. Goldstein" << endl << "3. McCormick" << endl << "4. EggHolder" << endl
         << "5. Beale" << endl;

    cin >> opcion;

    float (*ofp)(vector<float>); // Nuestro apuntador de funciones
    vector<float> lb, ub;

    switch (opcion) {
        case 1:{
            //Eliminamos y definimos los valores de los límites del espacio de busqueda (x, y)
            float (*ofp)(vector<float>)=sphereFun;
            actualizarParticulas(50, ofp, 1, _LB(-10), _UB(10));
        }
            break;
        case 2:
            ofp = &goldseinFun;
            actualizarParticulas(50, ofp, 2, _LB(-2), _UB(2));
            break;
        case 3:
            lb.clear();
            lb.push_back(-1.5);
            lb.push_back(-3);
            ofp = &mcCormickFun;
            actualizarParticulas(50 , ofp, 3, lb, _UB(4));
            break;
        case 4:
            ofp = &eggHolderFun;
            actualizarParticulas(50, ofp, 4, _LB(-512), _UB(512));
            break;
        case 5:
            ofp = &bealeFun;
            actualizarParticulas(50, ofp, 5, _LB(-4.5), _UB(4.5));
            break;
        case 100: //Case para probar las funciones de optimización con valores propuestos en https://en.wikipedia.org/wiki/Test_functions_for_optimization hacer caso omiso.
            cout << "Probando funciones de optimización: " << endl;
            cout << "Esfera: " << sphereFun({-10, 10}) << endl;
            cout << "Goldstein: " << goldseinFun({0, -1}) << endl;
            cout << "McCormick: " << mcCormickFun({-0.54719, -1.5419}) << endl;
            cout << "EggHolder: " << eggHolderFun({512, 404.2319}) << endl;
            cout << "Beale: " << bealeFun({3, 0.5}) << endl;
            break;
        default:
            cout << "Opcion " << opcion << " no válida en este ámbito." << endl;
            break;
    }
    return 0;
}
