close all

% Main parameters
rho = 1.024e6;           % average rate (bps)
delta = 445;             % standard deviation (bps) for t = 0.5 s
epsilon = 0.005;         % target probability
kappa = sqrt(2 * log(1 / epsilon));
duration = 3.0;          % duration in seconds
dt = 0.001;              % time step
t = (dt:dt:duration)';
n = length(t);
split = 6;               % 6 for O6 and 9 for O9

% Define overhead based on the split
if split == 6
    overhead = (137-128)*8;
elseif split == 9
    overhead = (978-128)*8;
else 
    error('Invalid value for split. Expected 6 or 9.');
end

runs = 60;  % number of executions

% Envelopes
envelope = (rho + overhead/0.001) * t + kappa * delta * sqrt(t);   % Brownian envelope
sigma = 1024;                                                      % burst for LB
envelope_lb = (rho + overhead/0.001) * t + sigma;                  % Leaky-Bucket envelope

% Time of interest (t.o.i.)
toi = (sigma / (kappa * delta))^2;
fprintf('\nTime of Interest (t.o.i.): %.4f s\n', toi);

% Initialization
all_traffics_increments = zeros(n, runs);
all_traffics_cumulative = zeros(n, runs);
violations = false(n, runs);
violation_instants = cell(runs, 1);

% Generate traffic and detect violations
for i = 1:runs
    noise = randn(n, 1);
    increments = rho * dt + delta * sqrt(dt) * noise;
    increments = increments + overhead;
    increments_bits = round(increments);
    all_traffics_increments(:, i) = increments_bits;
    cumulative_traffic = cumsum(increments_bits);
    all_traffics_cumulative(:, i) = cumulative_traffic;
    violations(:, i) = cumulative_traffic > envelope;
    violation_instants{i} = t(violations(:, i));
end

%% Main plot
figure;
hold on;

% Trajectories
for i = 1:runs
    plot(t, all_traffics_cumulative(:, i), 'Color', [0.3 0.3 1]);
    plot(t(violations(:, i)), all_traffics_cumulative(violations(:, i), i), ...
        'rx', 'MarkerSize', 6);
end

% Envelopes
plot(t, envelope, 'r--', 'LineWidth', 2);
plot(t, envelope_lb, 'g-.', 'LineWidth', 2);

% t.o.i. point
plot(toi, (rho + overhead/0.001) * toi + sigma, 'ko', 'MarkerSize', 8, 'MarkerFaceColor', 'k');

% Main settings
grid on;
xlabel('Time (s)');
ylabel('Cumulative bits');
% title('Traffic trajectories with deterministic and stochastic envelopes');
set(gca, 'FontSize', 14);

% Legend
h1 = plot(nan, nan, 'b-', 'LineWidth', 1.5);
h2 = plot(nan, nan, 'r--', 'LineWidth', 2);
h3 = plot(nan, nan, 'g-.', 'LineWidth', 2);
h4 = plot(nan, nan, 'ko', 'MarkerSize', 8, 'MarkerFaceColor', 'k');
h5 = plot(nan, nan, 'rx', 'MarkerSize', 6);
legend([h1 h2 h3 h4 h5], ...
    {'Traffic traces', 'Brownian envelope', 'Leaky-bucket envelope', 't.o.i.', 'Violations'}, ...
    'Location', 'northwest');

%% Zoom-in (magnifier inside the plot)

% Zoom range
xrange = [0.519 0.52];   % x-axis range
yrange = [10000 50000];  % y-axis range

% Create small axes (magnifier)
axLupa = axes('Position', [0.3, 0.3, 0.25, 0.25]); 
box on;
hold on;

% Index for region of interest
index = (t >= xrange(1)) & (t <= xrange(2));

% Trajectories inside magnifier
for i = 1:runs
    % Main trajectory in zoom
    plot(t(index), all_traffics_cumulative(index, i), 'Color', [0.3 0.3 1]);

    % Violations restricted to zoom interval
    violations_zoom = violations(:, i) & index;
    if any(violations_zoom)
        plot(t(violations_zoom), all_traffics_cumulative(violations_zoom, i), ...
            'rx', 'MarkerSize', 4);
    end
end

% Envelopes inside magnifier
plot(t(index), envelope(index), 'r--', 'LineWidth', 1.5);
plot(t(index), envelope_lb(index), 'g-.', 'LineWidth', 1.5);

% Adjust magnifier limits
xlim(xrange);
ylim(yrange);

set(gca, 'FontSize', 10);
grid on;
axis tight;
xlabel('');
ylabel('');
title('');
