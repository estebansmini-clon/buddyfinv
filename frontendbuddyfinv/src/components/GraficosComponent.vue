<template>
    <div class="graficos-container">
  
      <h1 class="titulo">GRÁFICAS</h1>
      <p class="descripcion">Visualiza tus ventas, productos estrella y gastos de forma cómoda y eficaz.</p>
  
      <div class="grid-graficos">
  
        <!-- VENTAS -->
        <div class="card-grafico">
          <div class="header-card ventas-color">VENTAS</div>
          <canvas id="ventasChart"></canvas>
          <button class="zoom-btn" @click="abrirModal('ventas')">🔍</button>
        </div>
  
        <!-- PRODUCTOS -->
        <div class="card-grafico">
          <div class="header-card productos-color">PRODUCTOS ESTRELLA</div>
          <canvas id="productosChart"></canvas>
          <button class="zoom-btn" @click="abrirModal('productos')">🔍</button>
        </div>
  
        <!-- GASTOS -->
        <div class="card-grafico">
          <div class="header-card gastos-color">GASTOS</div>
          <canvas id="gastosChart"></canvas>
          <button class="zoom-btn" @click="abrirModal('gastos')">🔍</button>
        </div>
  
      </div>
  
      <!-- MODAL -->
      <div v-if="modalActivo" class="modal-overlay" @click.self="cerrarModal">
        <div class="modal-content">
          <canvas id="modalChart"></canvas>
        </div>
      </div>
  
    </div>
  </template>
  
  
  <script>
  import { Chart, registerables } from "chart.js";
  import ChartDataLabels from "chartjs-plugin-datalabels";
  import { AnaliticasProvider } from "@/providers/AnaliticasProvider.js";
  
  Chart.register(...registerables, ChartDataLabels);
  
  export default {
    name: "GraficosComponent",
    data() {
      return {
        modalActivo: false,
        chartActivo: null,
        charts: {},
        modalChart: null,
        chartConfigs: {}
      };
    },
  
    async mounted() {
      await this.$nextTick();
      await this.cargarGraficos();
    },
  
    methods: {
      async cargarGraficos() {
        try {
          /* =====================================
             VENTAS (LÍNEAS)
          ======================================*/
          const ventas = await AnaliticasProvider.getVentasGraficos("2020-01-01", "2025-12-31");

// agrupar por fecha (YYYY-MM-DD)
const ventasPorDia = {};
ventas.forEach(v => {
  const fecha = v.period ? new Date(v.period).toISOString().split("T")[0] : "N/A";
  if (!ventasPorDia[fecha]) {
    ventasPorDia[fecha] = 0;
  }
  ventasPorDia[fecha] += v.totalVentas || 0;
});

const ventasLabels = Object.keys(ventasPorDia);
const ventasData = Object.values(ventasPorDia);

this.chartConfigs.ventas = {
  type: "line",
  data: {
    labels: ventasLabels,
    datasets: [{
      label: "Ventas Totales",
      data: ventasData,
      borderColor: "#e67e22",
      backgroundColor: "rgba(230,126,34,0.2)",
      fill: true
    }]
  },
  options: {
    plugins: {
      datalabels: { display: false },
      legend: { position: "right" }
    },
    scales: {
      x: {
        ticks: {
          maxRotation: 0,
          minRotation: 0
        }
      }
    }
  }
};

this.charts.ventas = new Chart(
  document.getElementById("ventasChart"),
  this.chartConfigs.ventas
);
  
          /* =====================================
             PRODUCTOS (BARRAS)
          ======================================*/
          const productos = await AnaliticasProvider.getProductosEstrella();
          const prodLabels = productos.map(p => p.nombreProducto);
          const prodData = productos.map(p => p.cantidadVendida);
  
          const totalProd = prodData.reduce((a,b) => a+b, 0);
          const maxProd = Math.max(...prodData);

this.chartConfigs.productos = {
  type: "bar",
  data: {
    labels: prodLabels,
    datasets: [{
      label: "Cantidad Vendida",
      data: prodData,
      backgroundColor: "#f39c12"
    }]
  },
  options: {
    plugins: {
      legend: { display: false },
      datalabels: {
        color: "#333",
        anchor: "end",
        align: "top",
        formatter: (value) => {
          const percentage = ((value/totalProd)*100).toFixed(1);
          return `${percentage}%`;
        }
      }
    },
    scales: {
      y: {
        suggestedMax: maxProd * 1.2 // techo 20% superior
      }
    }
  }
};
  
          this.charts.productos = new Chart(
            document.getElementById("productosChart"),
            this.chartConfigs.productos
          );
  
          /* =====================================
             GASTOS (PASTEL)
          ======================================*/
          const gastos = await AnaliticasProvider.getGastosGraficos();
          const gastoLabels = gastos.map(g => g.tipoEgreso);
          const gastoData = gastos.map(g => g.montoTotal);
  
          this.chartConfigs.gastos = {
            type: "pie",
            data: {
              labels: gastoLabels,
              datasets: [{
                label: "Gastos",
                data: gastoData,
                backgroundColor: [
                  "#97c2f9", "#92e0b8", "#f3c199", "#f5b041", "#f8c471",
                  "#a3e4d7", "#d7bde2", "#f9e79f", "#fadbd8"
                ]
              }]
            },
            options: {
              plugins: {
                legend: { position: "bottom" }, // fuera explica cada color
                datalabels: {
                  color: "#fff",
                  formatter: (value, ctx) => {
                    const total = ctx.dataset.data.reduce((a,b) => a+b, 0);
                    const percentage = ((value/total)*100).toFixed(1);
                    return `${percentage}%`; // solo porcentaje dentro
                  }
                }
              }
            }
          };
  
          this.charts.gastos = new Chart(
            document.getElementById("gastosChart"),
            this.chartConfigs.gastos
          );
  
        } catch (error) {
          console.error("Error cargando gráficos:", error);
        }
      },
  
      abrirModal(chartId) {
  this.modalActivo = true;

  this.$nextTick(() => {
    if (this.modalChart) {
      this.modalChart.destroy();
    }

    const ctx = document.getElementById("modalChart").getContext("2d");
    this.modalChart = new Chart(ctx, this.chartConfigs[chartId]);
  });
},
  
      cerrarModal() {
        this.modalActivo = false;
  
        if (this.modalChart) {
          this.modalChart.destroy();
          this.modalChart = null;
        }
      }
    }
  };
  </script>
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Outfit:wght@100..900&display=swap');
.graficos-container {
  padding: 20px;
  font-family: 'Outfit', sans-serif;
}

.titulo {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 5px;
  letter-spacing: 1px;
  color: #333; 
}

.descripcion {
  font-size: 14px;
  opacity: 0.8;
  margin-bottom: 30px;
  color: #333;
}

.grid-graficos {
  display: grid;
  gap: 20px;
}

.card-grafico {
  flex: 1;
  min-width: 0;
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0px 3px 12px rgba(0,0,0,0.08);
  position: relative;
  text-align: center;
}

.header-card {
  font-weight: bold;
  padding: 8px 0;
  border-radius: 10px;
  color: white;
  margin-bottom: 10px;
}

.ventas-color { background: #97c2f9; }
.productos-color { background: #92e0b8; }
.gastos-color { background: #f3c199; }

canvas {
  max-height: 250px;
}

.zoom-btn {
  position: absolute;
  right: 12px;
  bottom: 12px;
  background: #e67e22; /* mismo color que el título */
  color: #fff;
  border: none;
  font-size: 16px;
  padding: 8px 10px;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 2px 6px rgba(0,0,0,0.2);
  transition: transform 0.2s, background 0.2s;
}
.zoom-btn:hover {
  transform: scale(1.1);
  background: #d35400;
}

/* MODAL */

/* MODAL */
.modal-overlay {
  position: fixed;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: rgba(0,0,0,0.4);
  backdrop-filter: blur(6px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 99999;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 15px;
  width: 95%;          /* más ancho */
  max-width: 1400px;   /* límite mayor */
  height: 85%;         /* más alto */
  box-shadow: 0px 4px 20px rgba(0,0,0,0.3);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content canvas {
  width: 100% !important;
  height: 100% !important;
}
</style>  