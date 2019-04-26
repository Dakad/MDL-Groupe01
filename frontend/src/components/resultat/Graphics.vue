<template>
  <div class="graphics">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      v-if="bounds.minX"
      :width="width+'px'"
      :height="height+'px'"
    >
      <line
        v-for="(link,i) in graph.links"
        :key="'link_'+i"
        :x1="coords[link.source.index].x"
        :y1="coords[link.source.index].y"
        :x2="coords[link.target.index].x"
        :y2="coords[link.target.index].y"
        stroke="black"
        stroke-width="2"
      ></line>

      <circle
        v-for="(node, i) in graph.nodes"
        :key="'node_'+i"
        :cx="coords[i].x"
        :cy="coords[i].y"
        :r="50"
        :fill="'#fff'"
        stroke="black"
        stroke-width="1"
        @mousedown="currentMove = {x: $event.screenX, y: $event.screenY, node: node}"
      ></circle>

      <text
        v-for="(node, i) in graph.nodes"
        :key="'text_1_'+i"
        :x="coords[i].x"
        :y="coords[i].y"
        text-anchor="middle"
        class="labelNode"
        stroke-width="1"
        color="black"
      >{{node.name.substring(0,11)}}</text>

      <text
        v-for="(node, i) in graph.nodes"
        :key="'text_2_'+i"
        :x="coords[i].x"
        :y="coords[i].y + 15"
        text-anchor="middle"
        class="labelNode"
        stroke-width="1"
        color="black"
      >{{node.name.substring(12,22) + "..."}}</text>

      <text
        v-for="(link,i) in graph.links"
        :key="'text_3_'+i"
        :x="(coords[link.source.index].x + coords[link.target.index].x) / 2"
        :y="(coords[link.source.index].y + coords[link.target.index].y) / 2"
        text-anchor="middle"
        class="labelLink"
        color="black"
      >{{link.tag}}</text>
    </svg>
  </div>
</template>

<script>
import dummy from "@/services/dummy.json";
import * as d3 from "d3";

function nodeCreator() {
  console.log("enter node creator");
  let i;
  let nodes = [];
  for (i = 0; i < dummy.articles.length; i++) {
    nodes.push(dummy.articles[i].title);
  }
  console.log(nodes);
  return nodes;
}
export default {
  name: "Graphics",
  props: {
    articlesTitles: {
      type: Array,
      required: true,
      default: () => []
    },
    linkedArticles: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      // link: this.linker(dummy),
      graph: {},
      width: 700,
      height: 550,
      padding: 60,
      colors: [
        "#2196F3",
        "#E91E63",
        "#7E57C2",
        "#009688",
        "#00BCD4",
        "#EF6C00",
        "#4CAF50",
        "#FF9800",
        "#F44336",
        "#CDDC39",
        "#9C27B0"
      ],
      simulation: null,
      currentMove: null
    };
  },
  watch: {
    linkedArticles: "createGraph"
  },
  created() {
    this.createGraph();
  },

  computed: {
    bounds() {
      return {
        minX: Math.min(...this.graph.nodes.map(n => n.x)),
        maxX: Math.max(...this.graph.nodes.map(n => n.x)),
        minY: Math.min(...this.graph.nodes.map(n => n.y)),
        maxY: Math.max(...this.graph.nodes.map(n => n.y))
      };
    },
    coords() {
      return this.graph.nodes.map(node => {
        return {
          x:
            this.padding +
            ((node.x - this.bounds.minX) * (this.width - 2 * this.padding)) /
              (this.bounds.maxX - this.bounds.minX),
          y:
            this.padding +
            ((node.y - this.bounds.minY) * (this.height - 2 * this.padding)) /
              (this.bounds.maxY - this.bounds.minY)
        };
      });
    }
  },

  methods: {
    createGraph() {
      this.graph = {
        nodes: d3.range(this.articlesTitles.length).map(i => ({
          index: i,
          x: null,
          y: null,
          name: this.articlesTitles[i]
        })),
        links: d3.range(this.linkedArticles.length).map(i => ({
          source: Math.floor(Math.sqrt(i)),
          target: i + 1,
          tag: this.linkedArticles[i][2]
        }))
      };
      this.simulation = d3
        .forceSimulation(this.graph.nodes)
        .force("charge", d3.forceManyBody().strength(d => -100))
        .force("link", d3.forceLink(this.graph.links))
        .force("x", d3.forceX())
        .force("y", d3.forceY());
    }

    /*drag(e) {
        if (this.currentMove) {
          this.currentMove.node.fx = this.currentMove.node.x - (this.currentMove.x - e.screenX) * (this.bounds.maxX - this.bounds.minX) / (this.width - 2 * this.padding)
          this.currentMove.node.fy = this.currentMove.node.y - (this.currentMove.y - e.screenY) * (this.bounds.maxY - this.bounds.minY) / (this.height - 2 * this.padding)
          this.currentMove.x = e.screenX
          this.currentMove.y = e.screenY
        }
      },
      drop() {
        delete this.currentMove.node.fx
        delete this.currentMove.node.fy
        this.currentMove = null
        this.simulation.alpha(1)
        this.simulation.restart()
      }, */
    /*AIM to create a array with all the link possible between each one
     *  take the tags of one article and test it on all the following articles
     *  if similarities are found between two articles the tag is added + the two article
     * */
  }

  /*   props:{
      data : Object
    },
    components: {
    },
    data() {
      return {
        node: this.nodeCreator(dummy),
        link: this.linker(dummy),
        width: Math.max(document.documentElement.clientWidth, window.innerWidth || 0),
        height: Math.max(document.documentElement.clientHeight, window.innerHeight || 0) - 40,
        padding: 20,
        simulation: null,
        currentMove: null
      };

    },

    computed: {
      bounds() {
        return {
          minX: Math.min(...this.nodes.map(n => n.x)),
          maxX: Math.max(...this.nodes.map(n => n.x)),
          minY: Math.min(...this.nodes.map(n => n.y)),
          maxY: Math.max(...this.nodes.map(n => n.y))
        }
      },
      coords() {
        return this.nodes.map(node => {
          return {
            x: this.padding + (node.x - this.bounds.minX) * (this.width - 2*this.padding) / (this.bounds.maxX - this.bounds.minX),
            y: this.padding + (node.y - this.bounds.minY) * (this.height - 2*this.padding) / (this.bounds.maxY - this.bounds.minY)
          }
        })
      }
    },
    created(){
      this.simulation = d3.forceSimulation(this.nodes)
        .force('charge', d3.forceManyBody().strength(d => -100))
        .force('link', d3.forceLink(this.links))
        .force('x', d3.forceX())
        .force('y', d3.forceY())
    },
    methods: {
      drag(e) {
        if (this.currentMove) {
          this.currentMove.node.fx = this.currentMove.node.x - (this.currentMove.x - e.screenX) * (this.bounds.maxX - this.bounds.minX) / (this.width - 2 * this.padding)
          this.currentMove.node.fy = this.currentMove.node.y -(this.currentMove.y - e.screenY) * (this.bounds.maxY - this.bounds.minY) / (this.height - 2 * this.padding)
          this.currentMove.x = e.screenX
          this.currentMove.y = e.screenY
        }
      },
      drop(){
        delete this.currentMove.node.fx
        delete this.currentMove.node.fy
        this.currentMove = null
        this.simulation.alpha(1)
        this.simulation.restart()
      },
       */

  /*AIM to create a array with all the link possible between each one
   *  take the tags of one article and test it on all the following articles
   *  if similarities are found between two articles the tag is added + the two article
   * */

  /*
      linker() {
        let i;
        let motherTab = [];
        for (i = 0; i < dummy.articles.length; i++) {
          let toTest = dummy.articles[i].keywords;
          let j;
          for (j=i+1; j <  dummy.articles.length; j++){
            let childTab = "";
            let container = [];
            let test = false;
            let k;
            for (k=0; k < toTest.length; k++){
              let nameToTest = toTest[k].name;
              let l;
              for (l=0; l < dummy.articles[j].keywords.length; l++) {
                if (nameToTest === dummy.articles[j].keywords[l].name) {
                  childTab += nameToTest;
                  if (test === false) {
                    test = true;
                    container.push(dummy.articles[i].title);
                    container.push(dummy.articles[j].title);
                  }
                }
              }
            }
          container.push(childTab);
            if (container.length > 1){
              motherTab.push(container);
            }
          }
        }
        return motherTab;
      },

      nodeCreator() {
        let i;
        let nodes = [];
        for (i = 0; i < dummy.articles.length; i++){
          nodes.push(dummy.articles[i].title)
        }
        return nodes;
      }

    }, */
};
</script>


<style>
</style>
