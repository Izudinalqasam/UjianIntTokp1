package com.tokopedia.testproject.problems.androidView.graphTraversal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tokopedia.testproject.R;

import java.util.ArrayList;
import java.util.List;

import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Edge;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.energy.FruchtermanReingoldAlgorithm;

public class GraphActivity extends AppCompatActivity {
    private int nodeCount = 1;
    private Node currentNode;
    protected BaseGraphAdapter<ViewHolder> adapter;

    int[] nodeColor = {
            Color.rgb(128, 0, 128), // Purple
            Color.GREEN,
            Color.rgb(255, 165, 0), // Orange
            Color.BLUE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        final Graph graph = createGraph();
        setupAdapter(graph);
    }

    private void setupAdapter(Graph graph) {
        final GraphView graphView = findViewById(R.id.graph2);

        traverseAndColorTheGraph(graph, graph.getNode(0), 2);

        adapter = new BaseGraphAdapter<ViewHolder>(this, R.layout.node, graph) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.textView.setText(data.toString());

                viewHolder.cardView.setBackgroundColor(nodeColor[3]);

                if (data.toString().equals(masterRootNode.data.toString())) {
                    viewHolder.cardView.setBackgroundColor(nodeColor[0]);
                }

                for (int i = 0; i < listNodeLevel.size(); i++) {

                    for (int j = 0; j < listNodeLevel.get(i).size(); j++) {

                        if (data.toString().equals(listNodeLevel.get(i).get(j).data.toString())) {
                            viewHolder.cardView.setBackgroundColor(nodeColor[i + 1]);
                        }
                    }
                }
            }

        };

        adapter.setAlgorithm(new FruchtermanReingoldAlgorithm());

        graphView.setAdapter(adapter);
        graphView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentNode = adapter.getNode(position);
                adapter.notifyDataChanged(currentNode);
                Snackbar.make(graphView, "Clicked on " + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * TODO
         * Given input:
         * 1. a graph that represents a tree (there is no cyclic node),
         * 2. a rootNode, and
         * 3. a target distance,
         * you have to traverse the graph and give the color to each node as below criteria:
         * 1. RootNode is purple
         * 2. Nodes with the distance are less than the target distance are colored green
         * 3. Nodes with the distance are equal to the target distance are colored orange
         * 4. Other Nodes are blue
         */
    }

    private ArrayList<ArrayList<Node>> listNodeLevel = new ArrayList<>();
    private Node masterRootNode;

    private void traverseAndColorTheGraph(Graph graph, Node rootNode, int target) {
        List<Edge> allNode = graph.getEdges();
        Node tampungRoot = rootNode;
        masterRootNode = rootNode;

        listNodeLevel.clear();

        for (int i = 0; i < target; i++) {
            ArrayList<Node> childRoot = new ArrayList<>();

            if (listNodeLevel.size() > 0) {

                for (int j = 0; j < listNodeLevel.get(i - 1).size(); j++) {
                    tampungRoot = listNodeLevel.get(i - 1).get(j);

                    for (Edge e : allNode) {

                        if (e.getSource() == tampungRoot) {
                            childRoot.add(e.getDestination());
                        }
                    }
                }

            } else {
                for (Edge e : allNode) {

                    if (e.getSource() == tampungRoot) {
                        childRoot.add(e.getDestination());
                    }
                }
            }

            listNodeLevel.add(childRoot);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Graph createGraph() {
        final Graph graph = new Graph();
        final Node a = new Node(getNodeText());  //1
        final Node b = new Node(getNodeText());  //2
        final Node c = new Node(getNodeText());  //3
        final Node d = new Node(getNodeText());  //4
        final Node e = new Node(getNodeText());  //5
        final Node f = new Node(getNodeText());  //6
        final Node g = new Node(getNodeText());  //7

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        graph.addEdge(b, f);
        graph.addEdge(b, g);
        graph.addEdge(c, d);
        graph.addEdge(c, e);
        return graph;
    }

    private class ViewHolder {
        TextView textView;
        LinearLayout bgChanged;
        CardView cardView;

        ViewHolder(View view) {
            textView = view.findViewById(R.id.textView);
            bgChanged = view.findViewById(R.id.backgroud);
            cardView = view.findViewById(R.id.card_view);
        }
    }

    protected String getNodeText() {
        return "Node " + nodeCount++;
    }
}
